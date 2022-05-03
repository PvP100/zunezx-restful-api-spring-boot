package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Category;

public class CategoryView {
    private int id;
    private String title;
    private String imgUrl;
    private int totalCount;

    public CategoryView() {
    }

    public CategoryView(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.imgUrl = category.getImgUrl();
        this.totalCount = category.getCategoryTotalCount();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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

}
