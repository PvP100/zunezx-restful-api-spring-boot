package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Banner;

public class BannerView {
    private String id;
    private String imgUrl;

    public BannerView(Banner banner) {
        this.id = banner.getId();
        this.imgUrl = banner.getImgUrl();
    }

    public String getId() {
        return id;
    }

    public void setCategoryTitle(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
