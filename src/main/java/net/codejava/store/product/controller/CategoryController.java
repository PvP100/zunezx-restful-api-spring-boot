package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.dao.SaveClothesRepository;
import net.codejava.store.product.dao.*;
import net.codejava.store.product.models.data.Banner;
import net.codejava.store.product.models.data.Brand;
import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.view.*;
import net.codejava.store.response_model.*;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@Api(value = "candidate-api", description = "Nhóm API Category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
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

    String uniqueImgUrl;

    /**********************Category********************/
    @ApiOperation(value = "Lấy toàn bộ danh mục", response = Iterable.class)
    @GetMapping("/categories")
    public Response getAllClothes() {
        Response response;

        try {
            List<CategoryView> categoryView = categoryRepository.getAllCategory();
            response = new OkResponse(categoryView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/addCategory")
    @ApiOperation(value = "api thêm 1 danh mục sản phẩm", response = Iterable.class)
    public Response insertCategory(@RequestParam String title, @RequestParam(value = "categoryAvatar") MultipartFile avatar) {
        Response response;
        try {
            Category category = new Category();
            String uniqueCategoryUrl = UUID.randomUUID().toString();
            category.setTitle(title);
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
    public Response getHomeBanner() {
        Response response;

        try {
            List<BannerView> bannerViews = bannerRepository.getBanner();
            response = new OkResponse(bannerViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BannerErrorResponse(e.getLocalizedMessage());
        }
        return response;
    }

    @ApiOperation(value = "Lấy brand", response = Iterable.class)
    @GetMapping("/getBrand")
    public Response getBrand() {
        Response response;

        try {
            List<BrandView> bannerViews = brandRepository.getBrand();
            response = new OkResponse(bannerViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BannerErrorResponse(e.getLocalizedMessage());
        }
        return response;
    }

    @ApiOperation(value = "Lấy danh mục hiển thị màn Home", response = Iterable.class)
    @GetMapping("/getHomeCategory")
    public Response getHomeCategory(
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
            CategoryView categoryView;
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, 5);
            List<HomeCategoryView> homeCategoryViews = new ArrayList<>();
            List<CategoryView> categoryViews = categoryRepository.getCategoryCount();
            for (int i = 0; i < categoryViews.size(); i++) {
                categoryView = categoryViews.get(i);
                homeCategoryViews.add(
                        new HomeCategoryView(categoryView.getTitle(), productsRepository.getProductByCategory(pageable, categoryView.getId()).getContent())
                );
            }
            response = new OkResponse(homeCategoryViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new BannerErrorResponse(e.getLocalizedMessage());
        }
        return response;
    }


    @ApiOperation(value = "Api thêm mới Banner.", response = Iterable.class)
    @PostMapping("/addBanner/")
    Response addBanner(@RequestParam(value = "bannerAvatar") MultipartFile avatar){
        try{
            uniqueImgUrl = UUID.randomUUID().toString();
            Banner banner = new Banner();
            String avatarUrl = ProductController.uploadFile("banner/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            banner.setImgUrl(avatarUrl);
            bannerRepository.save(banner);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ServerErrorResponse();
        }
        return new OkResponse();
    }

    @ApiOperation(value = "Api xóa thương hiệu", response = Iterable.class)
    @DeleteMapping("/deleteBrand")
    public Response deleteBrand(@RequestParam("id") int id) {
        Response response;
        try {
            brandRepository.delete(id);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;

    }

    @ApiOperation(value = "Api thêm mới thương hiệu.", response = Iterable.class)
    @PostMapping("/addBrand/")
    Response addBrand(@RequestParam(value = "brandName") String brandName, @RequestParam(value = "brandAvatar") MultipartFile avatar){
        try{
            uniqueImgUrl = UUID.randomUUID().toString();
            Brand brand = new Brand();
            String avatarUrl = ProductController.uploadFile("brands/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            brand.setImgUrl(avatarUrl);
            brand.setBrandName(brandName);
            brandRepository.save(brand);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ServerErrorResponse();
        }
        return new OkResponse();
    }

    @PutMapping("/updateBanner")
    @ApiOperation(value = "api sửa Banner", response = Iterable.class)
    public Response updateBanner(@RequestParam String id, @RequestParam(value = "bannerAvatar") MultipartFile avatar) {
        Response response;
        try {
            Banner banner = bannerRepository.getOne(id);
            uniqueImgUrl = UUID.randomUUID().toString();
            String avatarUrl = ProductController.uploadFile("banner/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
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

    @DeleteMapping("/deleteCategory")
    @ApiOperation(value = "api xóa 1 danh mục sản phẩm", response = Iterable.class)
    public Response deleteCategory(@RequestParam("categoryId") int id) {
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

    @PutMapping("/updateCategory")
    @ApiOperation(value = "api sửa 1 danh mục sản phẩm", response = Iterable.class)
    public Response updateCategory(@RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("categoryAvatar") MultipartFile avatar) {
        Response response;
        try {
            Category category = categoryRepository.getOne(id);
            uniqueImgUrl = "dmhnc" + id;
            String avatarUrl = ProductController.uploadFile("category/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            category.setTitle(title);
            category.setImgUrl(avatarUrl);
            categoryRepository.save(category);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PutMapping("/updateBrand")
    @ApiOperation(value = "api sửa thương hiệu", response = Iterable.class)
    public Response updateBrand(@RequestParam("id") int id, @RequestParam("brandName") String title, @RequestParam("brandAvatar") MultipartFile avatar) {
        Response response;
        try {
            Brand brand = brandRepository.getOne(id);
            uniqueImgUrl = "thhnc" + id;
            String avatarUrl = ProductController.uploadFile("category/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            brand.setBrandName(title);
            brand.setImgUrl(avatarUrl);
            brandRepository.save(brand);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "api search category", response = Iterable.class)
    @GetMapping("/searchCategory/")
    public Response searchProduct(
            @RequestParam("categoryName") String categoryName,
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + "id")
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<CategoryView> categoryViews = categoryRepository.searchCategoryByName(pageable, categoryName);
            response = new OkResponse(categoryViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @ApiOperation(value = "api search category", response = Iterable.class)
    @GetMapping("/searchBrand/")
    public Response searchBrand(
            @RequestParam("brandName") String brandName,
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + "id")
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<BrandView> brandViews = brandRepository.searchBrandByName(pageable, brandName);
            response = new OkResponse(brandViews);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

}
