package net.codejava.store.product.models.view;

import java.util.List;

public class HomeCategoryView {

    private String categoryTitle;
    private List<ProductPreview> product;

    public HomeCategoryView(String categoryTitle, List<ProductPreview> product) {
        this.categoryTitle = categoryTitle;
        this.product = product;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<ProductPreview> getProduct() {
        return product;
    }

    public void setProduct(List<ProductPreview> product) {
        this.product = product;
    }
}
