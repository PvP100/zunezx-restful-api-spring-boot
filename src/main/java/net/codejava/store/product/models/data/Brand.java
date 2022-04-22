package net.codejava.store.product.models.data;

import javax.persistence.*;

@Entity
@Table(name = "Brand")
public class Brand {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String brandName;
    private String imgUrl;
    private int brandTotalCount = 0;

    public Brand() {
    }

    public Brand(int id, String imgUrl, String brandType) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.brandName = brandType;
    }

    public int getBrandTotalCount() {
        return brandTotalCount;
    }

    public void setBrandTotalCount(int brandTotalCount) {
        this.brandTotalCount = brandTotalCount;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandType) {
        this.brandName = brandType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
