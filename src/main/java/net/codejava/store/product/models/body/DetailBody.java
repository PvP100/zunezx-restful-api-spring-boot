package net.codejava.store.product.models.body;

import net.codejava.store.product.models.data.SizeProduct;

import java.util.List;

public class DetailBody {
    private String productId;
    private double price;
    private List<SizeProduct> size;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<SizeProduct> getSize() {
        return size;
    }

    public void setSize(List<SizeProduct> size) {
        this.size = size;
    }
}
