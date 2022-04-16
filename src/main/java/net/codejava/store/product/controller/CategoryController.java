package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.auth.dao.UserRespository;
import net.codejava.store.constants.Constant;
import net.codejava.store.customer.dao.CustomerRespository;
import net.codejava.store.customer.dao.SaveClothesRepository;
import net.codejava.store.product.dao.CategoryRepository;
import net.codejava.store.product.dao.ProductsRepository;
import net.codejava.store.product.dao.RateClothesRepository;
import net.codejava.store.product.dao.SubCategoryRepository;
import net.codejava.store.product.models.body.SubCategoryBody;
import net.codejava.store.product.models.body.UpdateCategoryBody;
import net.codejava.store.product.models.body.UpdateSubCategoryBody;
import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.data.SubCategory;
import net.codejava.store.product.models.view.CategoryView;
import net.codejava.store.product.models.view.SubCategoryView;
import net.codejava.store.response_model.ForbiddenResponse;
import net.codejava.store.response_model.OkResponse;
import net.codejava.store.response_model.Response;
import net.codejava.store.response_model.ServerErrorResponse;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

import static net.codejava.store.response_model.ResponseConstant.ErrorMessage.*;

@RestController
@RequestMapping("/api/category")
@Api(value = "candidate-api", description = "Nhóm API Category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
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

    @ApiOperation(value = "Lấy toàn bộ danh mục con", response = Iterable.class)
    @GetMapping("/get_all_sub_category")
    public Response getAllSubCategory(
            @ApiParam(name = "pageIndex", value = "Index trang, mặc định là 0")
            @RequestParam(value = "pageIndex", defaultValue = "0") Integer pageIndex,
            @ApiParam(name = "pageSize", value = "Kích thước trang, mặc đinh và tối đa là " + Constant.MAX_PAGE_SIZE)
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @ApiParam(name = "sortBy", value = "Trường cần sort, mặc định là " + SubCategory.TITLE)
            @RequestParam(value = "sortBy", defaultValue = SubCategory.TITLE) String sortBy,
            @ApiParam(name = "sortType", value = "Nhận (asc | desc), mặc định là desc")
            @RequestParam(value = "sortType", defaultValue = "desc") String sortType
    ) {
        Response response;

        try {
            Pageable pageable = PageAndSortRequestBuilder.createPageRequest(pageIndex, pageSize, sortBy, sortType, Constant.MAX_PAGE_SIZE);
            Page<SubCategoryView> subCategoryView = subCategoryRepository.getAllSubCategory(pageable);
            response = new OkResponse(subCategoryView);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

    @PostMapping("/addcategory")
    @ApiOperation(value = "api thêm 1 danh mục sản phẩm", response = Iterable.class)
    public Response insertCategory(String body) {
        Response response;
        try {
            List<Category> categoryList = categoryRepository.findAll();
            for (Category value : categoryList) {
                if (body.equalsIgnoreCase(value.getTitle()))
                    return new ForbiddenResponse(CATEGORY_IS_ALREADY_EXIST);
            }

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

    @PostMapping("/addSubCate")
    @ApiOperation(value = "api thêm 1 danh mục con", response = Iterable.class)
    public Response insertSubCategory(@RequestParam(name = "categoryId") String id, @RequestParam(name = "title") String title) {
        Response response;
        try {
            List<SubCategory> subCategoryList = subCategoryRepository.findAll();
            for (SubCategory value : subCategoryList) {
                if (title.equalsIgnoreCase(value.getTitle()))
                    return new ForbiddenResponse(SUBCATEGORY_IS_ALREADY_EXIST);
            }

            Category category = categoryRepository.findOne(id);
            SubCategory subCategory = new SubCategory(category, title);
            subCategoryRepository.save(subCategory);
            response = new OkResponse(subCategory);

        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }

        return response;
    }

    @PostMapping("/delcategory")
    @ApiOperation(value = "api xóa 1 danh mục sản phẩm", response = Iterable.class)
    public Response deleteCategory(String id) {
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

    @PostMapping("/delSubCategory")
    @ApiOperation(value = "api xóa 1 danh mục con", response = Iterable.class)
    public Response deleteSubCategory(Integer id) {
        Response response;
        try {
            subCategoryRepository.delete(id);
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
            List<Category> categoryList = categoryRepository.findAll();
            for (Category value : categoryList) {
                if (body.getTitle().equalsIgnoreCase(value.getTitle()))
                    return new ForbiddenResponse(CATEGORY_IS_ALREADY_EXIST);
            }

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

    @PutMapping("/upd_sub_category")
    @ApiOperation(value = "api sửa 1 danh mục con", response = Iterable.class)
    public Response updateSubCategory(@RequestBody UpdateSubCategoryBody body) {
        Response response;
        try {
            List<SubCategory> subCategoryList = subCategoryRepository.findAll();
            for (SubCategory value : subCategoryList) {
                if (body.getTitle().equalsIgnoreCase(value.getTitle()))
                    return new ForbiddenResponse(SUBCATEGORY_IS_ALREADY_EXIST);
            }
            SubCategory subCategory = subCategoryRepository.getOne(body.getId());
            subCategory.setTitle(body.getTitle());
            subCategoryRepository.save(subCategory);
            response = new OkResponse();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ServerErrorResponse();
        }
        return response;
    }

}
