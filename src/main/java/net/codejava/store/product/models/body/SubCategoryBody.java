package net.codejava.store.product.models.body;

public class SubCategoryBody {

    private String categoryId;

    private String title;

    public SubCategoryBody(String categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
