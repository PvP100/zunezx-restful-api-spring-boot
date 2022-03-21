package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Category;

public class CategoryView {
    private String id;
    private String title;

    public CategoryView() {
    }

    public CategoryView(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
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
}
