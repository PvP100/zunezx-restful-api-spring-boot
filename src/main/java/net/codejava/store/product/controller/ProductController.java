package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.dao.SaveClothesRepository;
import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.product.dao.CategoryRepository;
import net.codejava.store.product.dao.ProductsRepository;
import net.codejava.store.product.dao.RateClothesRepository;
import net.codejava.store.product.models.body.ClothesBody;
import net.codejava.store.product.models.data.*;
import net.codejava.store.product.models.view.ProductPreview;
import net.codejava.store.product.models.view.ProductViewModel;
import net.codejava.store.product.models.view.RateClothesViewModel;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(value = "candidate-api", description = "Nhóm API Customer, Yêu cầu access token của Khách hàng")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private SaveClothesRepository saveClothesRepository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private CustomerRespository customerRespository;
    @Autowired
    private RateClothesRepository rateClothesRepository;


    /**********************Clothes********************/
    @ApiOperation(value = "Lấy toàn bộ sản phẩm quần áo", response = Iterable.class)
    @GetMapping("/categorys/0")
    public Response getAllClothes(
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATED_DATE)
            @RequestParam(value = "sortBy", defaultValue = Product.CREATED_DATE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<ProductPreview> clothesPreviews = productsRepository.getAllClothesPreviews(pageable);
            response = new OkResponse(clothesPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @GetMapping("/categorys/{id}")
    public Response getProductBySubCate(
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATED_DATE)
            @RequestParam(value = "sortBy", defaultValue = Product.CREATED_DATE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType,
            @PathVariable("id") String id
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<ProductPreview> productPreviews = productsRepository.getProductByCategory(pageable, id);
            response = new OkResponse(productPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    /**********************detailClothes********************/
    @ApiOperation(value = "Lấy chi tiết sản phẩm", response = Iterable.class)
    @GetMapping("/{customerID}/clothes/{id}")
    public Response getDetailClothes(@PathVariable("customerID") String customerID,
                                     @PathVariable("id") String clothesID) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse("Customer not Exist");
            }
            Product product = productsRepository.findById(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }
            ProductViewModel productViewModel = productsRepository.getClothesViewModel(clothesID);
            productViewModel.setIsSaved(saveClothesRepository.existsByCustomer_IdAndClothes_Id(customerID, clothesID));
            response = new OkResponse(productViewModel);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @GetMapping("clothes/{id}")
    public Response getDetailClothesWithoutAuth(
            @PathVariable("id") String clothesID) {
        Response response;
        try {
            Product product = productsRepository.findById(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }
            ProductViewModel productViewModel = productsRepository.getClothesViewModel(clothesID);
            productViewModel.setIsSaved(false);
            response = new OkResponse(productViewModel);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }



    @ApiOperation(value = "api Thêm mới sản phẩm quần áo", response = Iterable.class)
    @PutMapping("/clothes/{id}")
    public Response insertClothes(@PathVariable("id") String categoryID,
                                  @RequestBody ClothesBody clothesBody) {
        Category category = categoryRepository.findOne(categoryID);
        if (category == null) {
            return new NotFoundResponse("Category not Exist");
        }
        Product product = new Product(clothesBody);
        product.setCategory(category);
        productsRepository.save(product);
        return new OkResponse();
    }

    /**********************similarClothes********************/
    @ApiOperation(value = "Lấy danh sách quần áo tưởng đương", response = Iterable.class)
    @GetMapping("/similarClothes/{id}")
    public Response getSimilarClothes(@PathVariable("id") String clothesID,
                                      @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
                                      @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
                                      @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                      @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATED_DATE)
                                      @RequestParam(value = "sortBy", defaultValue = Product.CREATED_DATE) String sortBy,
                                      @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
                                      @RequestParam(value = "sortType", defaultValue = "desc") String sortType) {
        Response response;
        try {
            Product product = productsRepository.findOne(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<ProductPreview> clothesPreviews = productsRepository.getSimilarClothesPreviews(pageable, product.getCategory().getId());
            response = new OkResponse(clothesPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    /**********************SaveClothes********************/

    @ApiOperation(value = "api Lưu sản phẩm", response = Iterable.class)
    @PostMapping("/{customerID}/saveClothes/{id}")
    public Response saveClothes(@PathVariable("customerID") String customerID, @PathVariable("id") String clothesID) {
        Response response;
        try {
            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse();
            }
            Product clothes = productsRepository.findById(clothesID);
            if (clothes == null) {
                return new NotFoundResponse("Clothes not Exist");
            }
            if (saveClothesRepository.existsByCustomer_IdAndClothes_Id(customerID, clothesID)) {
                return new ResourceExistResponse();
            }
            clothes.addSave();
            saveClothesRepository.save(new SaveClothes(clothes, customer));
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }
    @ApiOperation(value = "Api hủy lưu sản phẩm", response = Iterable.class)
    @DeleteMapping("/{customerID}/saveClothes/{id}/")
    public Response deleteSaveClothes(@PathVariable("customerID") String customerID, @PathVariable("id") String clothesID) {
        Response response;
        try {

            Customer customer = customerRespository.findOne(customerID);
            if (customer == null) {
                return new NotFoundResponse();
            }
            Product product = productsRepository.findOne(clothesID);
            if (product == null) {
                return new NotFoundResponse("Clothes not Exist");
            }
            if (!saveClothesRepository.existsByCustomer_IdAndClothes_Id(customerID, clothesID)) {
                return new ResourceExistResponse();
            }

            product.subSave();
            productsRepository.save(product);
            saveClothesRepository.deleteByCustomer_idAndAndClothes_Id(clothesID, customer.getId());
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;

    }

    /**********************rateClothes********************/
    @ApiOperation(value = "Api đánh giá sản phẩm", response = Iterable.class)
    @GetMapping("/rateClothes/{id}")
    public Response getAllRateClothes(@PathVariable("id") String clothesID,
                                      @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + RateClothes.RATE_DATE)
                                      @RequestParam(value = "sortBy", defaultValue = RateClothes.RATE_DATE) String sortBy,
                                      @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
                                      @RequestParam(value = "sortType", defaultValue = "desc") String sortType) {

        Product product = productsRepository.findById(clothesID);
        if (product == null) {
            return new NotFoundResponse("Clothes not Exist");
        }

        Sort sort =
                PageAndSortRequestBuilder.createSortRequest(sortBy, sortType);
        List<RateClothesViewModel> rateClothesViewModels = rateClothesRepository.getAllRate(clothesID, sort);
        return new OkResponse(rateClothesViewModels);
    }

    /**********************Clothes********************/
    @PostMapping("/categorys")
    @ApiOperation(value = "api thêm 1 danh mục sản phẩm", response = Iterable.class)
    @PutMapping("/categorys")
    public Response insertCategory(@RequestBody String body) {
        Response response;
        try {
            Category category = new Category();
            category.setTitle(body);
            categoryRepository.save(category);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }
    @ApiOperation(value = "Lấy tất cả danh mục sản phẩm", response = Iterable.class)
    @GetMapping("/category")
    public Response getAllCategory() {
        return new OkResponse(categoryRepository.findAll());
    }
//    private User getAuthenticatedUser() {
//        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        return userRespository.findByUsername(userEmail);
//    }
}
