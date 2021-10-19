package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Order;

import java.util.Date;

public class OrderPreview {
    private int id;
    private int isCheck;
    private Date createAt;

    public OrderPreview(Order order) {
        this.id = order.getId();
        this.createAt = order.getCreateAt();
        this.isCheck = order.getIsCheck();
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
