package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.dao.SaveClothesRepository;
import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.product.dao.BannerRepository;
import net.codejava.store.product.dao.CategoryRepository;
import net.codejava.store.product.dao.ProductsRepository;
import net.codejava.store.product.dao.RateClothesRepository;
import net.codejava.store.product.models.body.CategoryBody;
import net.codejava.store.product.models.body.UpdateCategoryBody;
import net.codejava.store.product.models.data.Banner;
import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.view.BannerView;
import net.codejava.store.product.models.view.CategoryView;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@Api(value = "candidate-api", description = "Nhóm API Category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BannerRepository bannerRepository;
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

    String uniqueBannerUrl;

    /**********************Category********************/
    @ApiOperation(value = "Lấy toàn bộ danh mục", response = Iterable.class)
    @GetMapping("/categories")
    public Response getAllClothes(
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Category.TITLE)
            @RequestParam(value = "sortBy", defaultValue = Category.TITLE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<CategoryView> categoryView = categoryRepository.getAllCategory(pageable);
            response = new OkResponse(categoryView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/addcategory")
    @ApiOperation(value = "api thêm 1 danh mục sản phẩm", response = Iterable.class)
    public Response insertCategory(@RequestParam String title, @RequestParam String categoryType, @RequestParam(value = "categoryAvatar") MultipartFile avatar) {
        Response response;
        try {
            if (!categoryRepository.checkCategoryType(categoryType).isEmpty()) {
                return new CategoryResponse(ResponseConstant.ErrorMessage.CATEGORY_IS_EXISTS);
            }
            Category category = new Category();
            String uniqueCategoryUrl = UUID.randomUUID().toString();
            category.setTitle(title);
            category.setCategoryType(categoryType);
            String avatarUrl = ProductController.uploadFile("category/" + uniqueCategoryUrl, uniqueCategoryUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            category.setImgUrl(avatarUrl);
            categoryRepository.save(category);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @ApiOperation(value = "Lấy banner màn home", response = Iterable.class)
    @GetMapping("/getBanner")
    public Response getHomeCategory(
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + Category.TITLE)
            @RequestParam(value = "sortBy", defaultValue = Category.TITLE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<BannerView> bannerViews = bannerRepository.getBanner(pageable);
            response = new OkResponse(bannerViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "Api upload ảnh đại diện khách hàng.", response = Iterable.class)
    @PostMapping("/addBanner/")
    Response addBanner(@RequestParam(value = "bannerAvatar") MultipartFile avatar){
        try{
            uniqueBannerUrl = UUID.randomUUID().toString();
            Banner banner = new Banner();
            String avatarUrl = ProductController.uploadFile("customers/" + uniqueBannerUrl, uniqueBannerUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            banner.setImgUrl(avatarUrl);
            bannerRepository.save(banner);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ServerErrorResponse();
        }
        return new OkResponse();
    }

    @PutMapping("/updateBanner")
    @ApiOperation(value = "api update Banner", response = Iterable.class)
    public Response updateBanner(@RequestParam String id, @RequestParam(value = "bannerAvatar") MultipartFile avatar) {
        Response response;
        try {
            Banner banner = bannerRepository.getOne(id);
            uniqueBannerUrl = UUID.randomUUID().toString();
            String avatarUrl = ProductController.uploadFile("customers/" + uniqueBannerUrl, uniqueBannerUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            banner.setImgUrl(avatarUrl);
            bannerRepository.save(banner);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PostMapping("/delcategory")
    @ApiOperation(value = "api xóa 1 danh mục sản phẩm", response = Iterable.class)
    public Response deleteCategory( String id) {
        Response response;
        try {
            categoryRepository.delete(id);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PutMapping("/updcategory")
    @ApiOperation(value = "api sửa 1 danh mục sản phẩm", response = Iterable.class)
    public Response updateCategory(@RequestBody UpdateCategoryBody body) {
        Response response;
        try {
            Category category = categoryRepository.getOne(body.getId());
            category.setTitle(body.getTitle());
            categoryRepository.save(category);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }




}
