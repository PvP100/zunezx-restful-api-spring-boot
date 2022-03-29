package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Brand;

public class BrandView {
    private String id;
    private String imgUrl;

    public BrandView(Brand brand) {
        this.id = "TH" + brand.getId();
        this.imgUrl = brand.getImgUrl();
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
