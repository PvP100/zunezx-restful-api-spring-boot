package net.codejava.store.product.models.view;

import net.codejava.store.product.models.data.Product;

import java.util.List;

public class CategoryPreview {
    private String productType;
    private List<ProductPreview> listP;

    public CategoryPreview(Product body) {
        this.productType = body.getDescription();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<ProductPreview> getListP() {
        return listP;
    }

    public void setListP(List<ProductPreview> listP) {
        this.listP = listP;
    }
}
