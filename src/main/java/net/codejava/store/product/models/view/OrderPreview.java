package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Order;

import java.util.Date;

public class OrderPreview {
    private String id;
    private Date createAt;

    public OrderPreview(Order order) {
        this.id = order.getId();
        this.createAt = order.getCreateAt();
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
