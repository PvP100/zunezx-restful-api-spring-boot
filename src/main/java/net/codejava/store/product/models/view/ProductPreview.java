package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.data.SizeProduct;

import java.util.List;

public class ProductPreview {
    private String id;
    private String name;
    private double price;
    private String subCategory;
    private String logoUrl;
    private List<SizeProduct> size;
    private int numberSave;
    private int isSale;
    private float salePercent;
    private List<String> listCover;

    public ProductPreview() {
    }

    public ProductPreview(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.logoUrl = product.getAvatarUrl();
        this.numberSave = product.getTotalSave();
        this.subCategory= product.getSubCategory().getTitle();
        this.size = product.getSize();
        this.isSale = product.getIsSale();
        this.salePercent = product.getSalePercent();
        this.listCover = product.getCoverUrl();
    }

    public List<String> getListCover() {
        return listCover;
    }

    public void setListCover(List<String> listCover) {
        this.listCover = listCover;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }

    public float getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(float salePercent) {
        this.salePercent = salePercent;
    }

    public List<SizeProduct> getSize() {
        return size;
    }

    public void setSize(List<SizeProduct> size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getNumberSave() {
        return numberSave;
    }

    public void setNumberSave(int numberSave) {
        this.numberSave = numberSave;
    }
}
