package net.codejava.store.product.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.StorageException;
import com.google.firebase.cloud.StorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.product.dao.*;
import net.codejava.store.product.models.data.*;
import net.codejava.store.product.models.view.*;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@Api(value = "candidate-api", description = "Nhóm API Product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private CustomerRespository customerRespository;
    @Autowired
    private OrderRepository orderRepository;

    //    @Value("${file.upload-dir}")
    String FILE_DIRECTORY = "D:\\AndroidProjects-4.2\\Zunezx\\API\\store\\ptit.store\\src\\main\\resources\\static\\uploadImages";

    /**********************Clothes********************/
    @ApiOperation(value = "Lấy toàn bộ sản phẩm quần áo", response = Iterable.class)
    @GetMapping("/getProduct")
    public Response getAllClothes(
            @RequestParam(value = "categoryId", defaultValue = "0") int categoryId,
            @RequestParam(value = "isSale", defaultValue = "0") int isSale,
            @RequestParam(value = "brandId", defaultValue = "0") int brandId,
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
            Page<ProductPreview> productPreviews = productsRepository.getAllClothesPreviews(pageable);
            if (isSale == 1) {
                productPreviews = productsRepository.getAllProduct(pageable, isSale);
            }
            if (brandId != 0 && categoryId != 0) {
                if (isSale == 1) {
                    productPreviews = productsRepository.getSaleProductByCategoryAndBrand(pageable, categoryId, brandId, isSale);
                } else {
                    productPreviews = productsRepository.getProductByCategoryAndBrand(pageable, categoryId, brandId);
                }
            } else if (brandId != 0 && categoryId == 0) {
                if (isSale == 1) {
                    productPreviews = productsRepository.getSaleProductByBrand(pageable, brandId, isSale);
                } else {
                    productPreviews = productsRepository.getProductByBrand(pageable, brandId);
                }
            } else if (brandId == 0 && categoryId != 0) {
                if (isSale == 1) {
                    productPreviews = productsRepository.getSaleProductByCategory(pageable, categoryId, isSale);
                } else {
                    productPreviews = productsRepository.getProductByCategory(pageable, categoryId);
                }
            }


            response = new OkResponse(productPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @GetMapping("/subcate")
//    public Response getSubCategory(
//            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
//            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
//            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
//            @RequestParam(value = "pageSize", required = false) Integer pageSize,
//            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATED_DATE)
//            @RequestParam(value = "sortBy", defaultValue = Product.CREATED_DATE) String sortBy,
//            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
//            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
//    ) {
//        Response response;
//
//        try {
//            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
//            Page<CategoryPreview> clothesPreviews = productsRepository.getSubCate(pageable);
//            response = new OkResponse(clothesPreviews);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }
    /**********************detailClothes********************/
//    @ApiOperation(value = "Lấy chi tiết sản phẩm", response = Iterable.class)
//    @GetMapping("/{customerID}/clothes/{id}")
//    public Response getDetailClothes(@PathVariable("customerID") String customerID,
//                                     @PathVariable("id") String clothesID) {
//        Response response;
//        try {
//            Customer customer = customerRespository.findOne(customerID);
//            if (customer == null) {
//                return new NotFoundResponse("Customer not Exist");
//            }
//            Product product = productsRepository.findById(clothesID);
//            if (product == null) {
//                return new NotFoundResponse("Clothes not Exist");
//            }
//            ProductViewModel clothesViewModel = productsRepository.getClothesViewModel(clothesID);
//            clothesViewModel.setIsSaved(saveClothesRepository.existsByCustomer_IdAndProduct_Id(customerID, clothesID));
//            response = new OkResponse(clothesViewModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }
    @ApiOperation(value = "Lấy chi tiết sản phẩm", response = Iterable.class)
    @GetMapping("product/{id}")
    public Response getDetailClothesWithoutAuth(
            @PathVariable("id") String productId) {
        Response response;
        try {
            Product product = productsRepository.findById(productId);
            if (product == null) {
                return new NotFoundResponse("Product not Exist");
            }
            ProductPreview productPreview = productsRepository.getProductDetail(productId);
            response = new OkResponse(productPreview);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "api Thêm mới sản phẩm", response = Iterable.class)
    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Response insertClothes(@RequestParam("categoryId") int categoryID,
                                  @RequestParam("brandId") int brandId,
                                  @RequestParam(value = "name",required = true) String name,
                                  @RequestParam(value = "description",required = true) String description,
                                  @RequestParam(value = "price",required = true) double price,
                                  @RequestParam(value = "salePrice",required = true) double salePrice,
                                  @RequestParam(value = "isSale") int isSale,
                                  @RequestParam(value = "quantity",required = true) int quantity,
                                  @RequestParam(value = "avatar",required = true) MultipartFile avatar,
                                  @RequestParam(value = "warranty",required = true) String warranty) {
        Category category = categoryRepository.findOne(categoryID);
        Brand brand = brandRepository.findOne(brandId);
        if (category == null) {
            return new NotFoundResponse("Category not Exist");
        }
        if (brand == null) {
            return new NotFoundResponse("Thương hiệu không tồn tại");
        }


        Product product = new Product(name, (long) price, description, quantity, isSale, warranty);
        product.setCategory(category);
        product.setBrand(brand);
        if (isSale == 1) {
            if (salePrice > price) {
                return new ForbiddenResponse("Giá khuyến mãi phải nhỏ hơn giá gốc");
            }
            double percent = (salePrice / price) * 100;
            product.setSalePrice((long) salePrice);
            product.setSalePercent(100 - (long) percent);
        }
        categoryRepository.plusTotal(categoryID, quantity);
        brandRepository.plusTotal(brandId, quantity);
        productsRepository.save(product);
        if(avatar != null){
            try {
                String productID = productsRepository.getProductByName(name);
                String avatarUrl = uploadFile("products/" + productID, productID + "_avatar.jpg",
                        avatar.getBytes(), "image/jpeg");
                Product product1 = productsRepository.getOne(productID);
                product1.setAvatarUrl(avatarUrl);
                productsRepository.save(product1);
            }catch (IOException e) {
                e.printStackTrace();
                return new ResourceExistResponse("tên sản phẩm đã tồn tại");
            }
        }

        return new OkResponse();
    }

    public static String uploadFile(String dir, String fileName,
                                    byte[] data,
                                    String contentType) throws StorageException {
        Blob avatarFile = StorageClient.getInstance()
                .bucket()
                .create(dir+"/"+fileName, data, contentType);
        return getDownloadUrl(avatarFile.getBucket(), avatarFile.getName());
    }

    public static String getDownloadUrl(String bucketUrl, String fileName) {
        return "http://storage.googleapis.com/" + bucketUrl + "/" + fileName;
    }

    @ApiOperation(value = "api update sản phẩm", response = Iterable.class)
    @RequestMapping(path = "/updateProduct/{productId}", method = RequestMethod.PUT, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Response updateClothes(@RequestParam("categoryId") int categoryID,
                                  @RequestParam("brandId") int brandId,
                                  @PathVariable("productId") String productID,
                                  @RequestParam(value = "name",required = true) String name,
                                  @RequestParam(value = "description",required = true) String description,
                                  @RequestParam(value = "price",required = true) double price,
                                  @RequestParam(value = "salePrice",required = true) double salePrice,
                                  @RequestParam(value = "isSale") int isSale,
                                  @RequestParam(value = "quantity",required = true) int quantity,
                                  @RequestParam(value = "avatar",required = true) MultipartFile avatar,
                                  @RequestParam(value = "warranty",required = true) String warranty) {
        Product product = productsRepository.findOne(productID);
        if (product == null) {
            return new NotFoundResponse("Clothes not Exist");
        }
        try {
            if (name != null) product.setName(name);
            if (description != null) product.setDescription(description);
            Category category = categoryRepository.findOne(categoryID);
            if (category != null) {
                product.setCategory(category);
            }
            Brand brand = brandRepository.findOne(brandId);
            if (brand != null) {
                product.setBrand(brand);
            }
            if (price != -1) product.setPrice((long) price);
            if (quantity != -1) product.setQuantity(quantity);
            if (!avatar.isEmpty()){
                String avatarUrl = uploadFile("products/" + productID, productID + "_avatar.jpg",
                        avatar.getBytes(), "image/jpeg");
                product.setAvatarUrl(avatarUrl);
            }
            double percent = (salePrice / price) * 100;
            if (isSale == 0) {
                product.setSalePercent(0);
                product.setSalePrice(0);
            } else {
                product.setSalePercent(100 - (long) percent);
                product.setSalePrice((long) salePrice);
            }
            productsRepository.save(product);
        } catch (IOException e){
            e.printStackTrace();
            return new ServerErrorResponse();
        }

        return new OkResponse();
    }

    @ApiOperation(value = "api xóa sản phẩm", response = Iterable.class)
    @PostMapping("/deleteProduct/")
    public Response deleteProduct(String id) {
        Response response;
        try {
            productsRepository.delete(id);
            response = new OkResponse();
        } catch (Exception e){
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    @ApiOperation(value = "api update sale", response = Iterable.class)
//    @PutMapping("/updatesale/{id}")
//    public Response updateSale(@PathVariable("id") String id,
//                               long salePercent) {
//        Response response;
//        try {
//            Product p = productsRepository.getOne(id);
//            p.setIsSale(1);
//            p.setSalePercent(salePercent);
//            productsRepository.save(p);
//            response = new OkResponse();
//        } catch (Exception e){
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }
//
//    @ApiOperation(value = "api update sale", response = Iterable.class)
//    @PutMapping("/removesale/{id}")
//    public Response removeSale(@PathVariable("id") String id) {
//        Response response;
//        try {
//            Product p = productsRepository.getOne(id);
//            p.setIsSale(0);
//            p.setSalePercent(0);
//            productsRepository.save(p);
//            response = new OkResponse();
//        } catch (Exception e){
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

//    @ApiOperation(value = "api count product", response = Iterable.class)
//    @PostMapping("/countSubCate/")
//    public Response countBySubCate() {
//        Response response;
//        try {
//            long product = productsRepository.count();
//            long order = orderRepository.count();
//            long category = categoryRepository.count();
//            long brand = brandRepository.count();
//            List<StaticView> list = new ArrayList<>();
//
//            list.add(new StaticView("product", product));
//            list.add(new StaticView("category", category));
//            list.add(new StaticView("order", order));
//            list.add(new StaticView("brand", brand));
//
//            response = new OkResponse(list);
//        } catch (Exception e){
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    @ApiOperation(value = "api search product", response = Iterable.class)
    @GetMapping("/searchProduct/{productName}")
    public Response searchProduct(
            @PathVariable("productName") String productName,
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
            Page<ProductPreview> clothesPreviews = productsRepository.searchByName(pageable, productName);
            response = new OkResponse(clothesPreviews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "api Thống kê", response = Iterable.class)
    @GetMapping("/statistic/")
    public Response Statistic () {
        Response response;
        try {
//            List<Object[]> data = productsRepository.countBySubCate();
//            Map<String, BigDecimal> map = null;
//            if (data != null && !data.isEmpty()){
//                map = new HashMap<>();
//                for (Object[] object: data) {
//                    map.put((String) object[0], (BigDecimal) object[1]);
//                }
//            }

            long totalProduct = productsRepository.count();
            long totalOrder = orderRepository.count();
            long orderChecked = orderRepository.countByIsCheck(1);
            long orderUnchecked = orderRepository.countByIsCheck(0);
            long orderCanceled = orderRepository.countByIsCheck(-1);
            long totalRevenue = orderRepository.getTotalPrice();
//            Double totalIncome = orderRepository.totalIncome();
            ThongKeView view = new ThongKeView(totalProduct, totalOrder, orderChecked, orderUnchecked, orderCanceled, totalRevenue);
            view.setTotalCategory((int) categoryRepository.count());
            view.setTotalBrand((int) brandRepository.count());

            AtomicInteger categoryIndex = new AtomicInteger();
            AtomicInteger brandIndex = new AtomicInteger();

            view.setCategory(
                    categoryRepository.getListCategory().stream().map( it ->
                            new StaticView(categoryIndex.incrementAndGet(), it.getTitle(), it.getTotalCount())).collect(Collectors.toList()
                    )
            );
            view.setBrand(brandRepository.getAllBrand().stream().map( it -> new StaticView(brandIndex.incrementAndGet(), it.getBrandName(), it.getTotalCount())).collect(Collectors.toList()));
            response = new OkResponse(view);
        } catch (Exception e){
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

//    /**********************similarClothes********************/
//    @ApiOperation(value = "Lấy danh sách quần áo tưởng đương", response = Iterable.class)
//    @GetMapping("/similarClothes/{id}")
//    public Response getSimilarClothes(@PathVariable("id") String clothesID,
//                                      @ApiParam(name = "pageIndex", value = "index trang, mặc định là 0")
//                                      @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
//                                      @ApiParam(name = "pageSize", value = "Kích thước trang, mặc định và tối đa là " + Constant.MAX_PAGE_SIZE)
//                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
//                                      @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Product.CREATED_DATE)
//                                      @RequestParam(value = "sortBy", defaultValue = Product.CREATED_DATE) String sortBy,
//                                      @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
//                                      @RequestParam(value = "sortType", defaultValue = "desc") String sortType) {
//        Response response;
//        try {
//            Product product = productsRepository.findOne(clothesID);
//            if (product == null) {
//                return new NotFoundResponse("Clothes not Exist");
//            }
//            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
//            Page<ProductPreview> clothesPreviews = productsRepository.getSimilarClothesPreviews(pageable, product.getCategory().getId());
//            response = new OkResponse(clothesPreviews);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }

    /**********************SaveClothes********************/

//    @ApiOperation(value = "api Lưu sản phẩm", response = Iterable.class)
//    @PostMapping("/{customerID}/saveClothes/{id}")
//    public Response saveClothes(@PathVariable("customerID") String customerID, @PathVariable("id") String clothesID) {
//        Response response;
//        try {
//            Customer customer = customerRespository.findOne(customerID);
//            if (customer == null) {
//                return new NotFoundResponse();
//            }
//            Product product = productsRepository.findById(clothesID);
//            if (product == null) {
//                return new NotFoundResponse("Clothes not Exist");
//            }
//            if (saveClothesRepository.existsByCustomer_IdAndProduct_Id(customerID, clothesID)) {
//                return new ResourceExistResponse();
//            }
//            product.addSave();
//            saveClothesRepository.save(new SaveClothes(product, customer));
//            response = new OkResponse();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//    }
//    @ApiOperation(value = "Api hủy lưu sản phẩm", response = Iterable.class)
//    @DeleteMapping("/{customerID}/saveClothes/{id}/")
//    public Response deleteSaveClothes(@PathVariable("customerID") String customerID, @PathVariable("id") String clothesID) {
//        Response response;
//        try {
//
//            Customer customer = customerRespository.findOne(customerID);
//            if (customer == null) {
//                return new NotFoundResponse();
//            }
//            Product product = productsRepository.findOne(clothesID);
//            if (product == null) {
//                return new NotFoundResponse("Clothes not Exist");
//            }
//            if (!saveClothesRepository.existsByCustomer_IdAndProduct_Id(customerID, clothesID)) {
//                return new ResourceExistResponse();
//            }
//
//            product.subSave();
//            productsRepository.save(product);
//            saveClothesRepository.deleteByCustomer_idAndProduct_Id(clothesID, customer.getId());
//            response = new OkResponse();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response = new ServerErrorResponse();
//        }
//        return response;
//
//    }


//    @ApiOperation(value = "Lấy tất cả danh mục sản phẩm", response = Iterable.class)
//    @GetMapping("/category")
//    public Response getAllCategory() {
//        return new OkResponse(categoryRepository.findAll());
//    }
////    private User getAuthenticatedUser() {
////        String userEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
////        return userRespository.findByUsername(userEmail);
////    }
}

