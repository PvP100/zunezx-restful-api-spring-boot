package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Category;

public class CategoryView {
    private int id;
    private String title;
    private int quantity;
    private String imgUrl;

    public CategoryView() {
    }

    public CategoryView(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.quantity = category.getQuantity();
        this.imgUrl = category.getImgUrl();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

}
