package net.codejava.store.product.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.codejava.store.product.dao.BannerRepository;
import net.codejava.store.product.models.data.Banner;
import net.codejava.store.product.models.view.BannerView;
import net.codejava.store.response_model.BannerErrorResponse;
import net.codejava.store.response_model.OkResponse;
import net.codejava.store.response_model.Response;
import net.codejava.store.response_model.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/banner")
@Api(value = "candidate-api", description = "Nhóm API Banner quảng cáo")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    BannerRepository bannerRepository;

    String uniqueImgUrl;

    @ApiOperation(value = "Api thêm mới Banner.", response = Iterable.class)
    @PostMapping("/addBanner/")
    Response addBanner(@RequestParam(value = "bannerAvatar") MultipartFile avatar, @RequestParam(value = "url") String linkUrl){
        try{
            uniqueImgUrl = UUID.randomUUID().toString();
            Banner banner = new Banner();
            String avatarUrl = ProductController.uploadFile("banner/" + uniqueImgUrl, uniqueImgUrl + "_avatar.jpg",
                    avatar.getBytes(), "image/jpeg");
            banner.setImgUrl(avatarUrl);
            banner.setLinkUrl(linkUrl);
            bannerRepository.save(banner);
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

}
