package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.SubCategory;

public class SubCategoryView {

    private Integer id;

    private String subCateTittle;

    private String cateTitle;

    public SubCategoryView(SubCategory subCategory) {
        this.id = subCategory.getSubCategoryId();
        this.subCateTittle = subCategory.getTitle();
        this.cateTitle = subCategory.getCategory().getTitle();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubCateTittle() {
        return subCateTittle;
    }

    public void setSubCateTittle(String subCateTittle) {
        this.subCateTittle = subCateTittle;
    }

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }

    public SubCategoryView(Integer id, String subCateTittle, String cateTitle) {
        this.id = id;
        this.subCateTittle = subCateTittle;
        this.cateTitle = cateTitle;
    }

    public SubCategoryView() {
    }
}
