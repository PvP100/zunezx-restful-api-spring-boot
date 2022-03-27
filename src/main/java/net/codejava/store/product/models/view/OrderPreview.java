package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Order;

import java.util.Date;

public class OrderPreview {
    private String id;
    private int isCheck;
    private Date createAt;
    private int totalProduct;

    public OrderPreview(Order order) {
        this.id = "HNC" + order.getId();
        this.createAt = order.getCreateAt();
        this.isCheck = order.getIsCheck();
        this.totalProduct = order.getTotalProduct();
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
