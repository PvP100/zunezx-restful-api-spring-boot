package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Category;

public class CategoryView {
    private String id;
    private String title;
    private int quantity;
    private String imgUrl;

    public CategoryView() {
    }

    public CategoryView(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.title = category.getImgUrl();
        this.quantity = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
