package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Order;

import java.util.Date;

public class OrderPreview {
    private int id;
    private int isCheck;
    private Date createdDate;
    private Date updateDate;
    private int totalProduct;

    public OrderPreview(Order order) {
        this.id = order.getId();
        this.createdDate = order.getCreateAt();
        this.updateDate = order.getUpdateAt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
