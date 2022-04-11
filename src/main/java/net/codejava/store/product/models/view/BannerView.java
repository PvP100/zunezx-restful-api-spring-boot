package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Banner;

public class BannerView {
    private int id;
    private String imgUrl;

    public BannerView(Banner banner) {
        this.id = banner.getId();
        this.imgUrl = banner.getImgUrl();
    }

    public int getId() {
        return id;
    }

    public void setCategoryTitle(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
