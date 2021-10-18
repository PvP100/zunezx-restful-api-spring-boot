package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Order;
import net.codejava.store.product.models.data.OrderDetail;

public class OrderDetailView {
    private String id;

    private String orderId;
    private String productId;
    private int quantity;
    private double price;
    private double total;
    private String size;

    public OrderDetailView(OrderDetail detail) {
        this.id = detail.getId();
        this.orderId = detail.getOrder().getId();
        this.productId = detail.getProduct().getId();
        this.quantity = detail.getQuantity();
        this.price = detail.getPrice();
        this.total = (double) quantity * price;
        this.size = detail.getSize();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
