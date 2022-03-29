package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Brand;

public class BrandView {
    private int id;
    private String imgUrl;
    private String brandType;

    public BrandView(Brand brand) {
        this.id = brand.getId();
        this.imgUrl = brand.getImgUrl();
        this.brandType = brand.getBrandType();
    }

    public int getId() {
        return id;
    }

    public void setCategoryTitle(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
