package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.codejava.store.constants.Constant;
import net.codejava.store.product.dao.BrandRepository;
import net.codejava.store.product.models.data.Brand;
import net.codejava.store.product.models.view.BrandView;
import net.codejava.store.response_model.BannerErrorResponse;
import net.codejava.store.response_model.OkResponse;
import net.codejava.store.response_model.Response;
import net.codejava.store.response_model.ServerErrorResponse;
import net.codejava.store.utils.PageAndSortRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brand")
@Api(value = "candidate-api", description = "Nhóm API Thương hiệu")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    String uniqueImgUrl;

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
    @PostMapping("/addBrand")
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

    @ApiOperation(value = "api tìm thương hiệu", response = Iterable.class)
    @GetMapping("/searchBrand")
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
