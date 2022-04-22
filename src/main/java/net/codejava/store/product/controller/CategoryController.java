package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
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
    private ProductsRepository productsRepository;
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private CustomerRespository customerRespository;


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

}
