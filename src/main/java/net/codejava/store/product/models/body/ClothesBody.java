package net.codejava.store.product.models.body;

import net.codejava.store.product.models.data.SizeProduct;

import java.util.List;

public class ClothesBody {

    private String name;
    private double price;
    private String description;
    private List<SizeProduct> size;

    public List<SizeProduct> getSize() {
        return size;
    }

    public void setSize(List<SizeProduct> size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}