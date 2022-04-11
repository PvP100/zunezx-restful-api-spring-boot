package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.OrderDetail;

public class OrderDetailView {

    private String productId;
    private int quantity;
    private double price;
    private double total;
    private String productUrl;
    private String productName;

    public OrderDetailView(OrderDetail detail) {
        this.productName = detail.getProduct().getName();
        this.productUrl = detail.getProduct().getAvatarUrl();
        this.productId = detail.getProduct().getId();
        this.quantity = detail.getQuantity();
        this.price = detail.getPrice();
        this.total = detail.getTotal();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
