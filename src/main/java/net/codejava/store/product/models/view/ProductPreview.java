package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Product;

public class ProductPreview {
    private String id;
    private String name;
    private double price;
    private String category;
    private String brand;
    private String logoUrl;
    private int numberSave;
    private int quantity;
    private int isSale;
    private float salePercent;
    private double salePrice;
    private String warranty;

    public ProductPreview() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ProductPreview(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.logoUrl = product.getAvatarUrl();
        this.numberSave = product.getTotalSave();
        this.category= product.getCategory().getTitle();
        this.brand= product.getBrand().getBrandName();
        this.quantity = product.getQuantity();
        this.isSale = product.getIsSale();
        this.salePercent = product.getSalePercent();
        this.warranty = product.getWarranty();
        this.salePrice = product.getSalePrice();
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
