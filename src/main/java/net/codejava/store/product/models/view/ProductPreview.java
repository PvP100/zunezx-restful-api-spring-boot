package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Brand;
import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.data.Product;

public class ProductPreview {
    private String id;
    private String name;
    private double price;
    private Category category;
    private Brand brand;
    private String logoUrl;
    private int quantity;
    private String description;
    private int isSale;
    private double salePercent;
    private double salePrice;
    private String warranty;

    public ProductPreview() {
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductPreview(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.logoUrl = product.getAvatarUrl();
        this.category= product.getCategory();
        this.brand= product.getBrand();
        this.quantity = product.getQuantity();
        this.isSale = product.getIsSale();
        this.salePercent = product.getSalePercent();
        this.warranty = product.getWarranty();
        this.salePrice = product.getSalePrice();
        this.description = product.getDescription();
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

    public double getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(double salePercent) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
}
