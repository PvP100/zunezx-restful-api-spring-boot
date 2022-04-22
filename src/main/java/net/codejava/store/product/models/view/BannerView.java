package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Banner;

public class BannerView {
    private int id;
    private String imgUrl;
    private String linkUrl;

    public BannerView(Banner banner) {
        this.id = banner.getId();
        this.imgUrl = banner.getImgUrl();
        this.linkUrl = banner.getLinkUrl();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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
