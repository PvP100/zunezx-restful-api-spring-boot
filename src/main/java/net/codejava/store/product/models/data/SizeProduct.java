package net.codejava.store.product.models.data;

import javax.persistence.*;

@Entity
@Table(name = "SizeProduct")
public class SizeProduct {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sizeProductId;
    private String size;
    private int quantity;

    public SizeProduct(String size, int quantity) {
        this.size = size;
        this.quantity = quantity = 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSizeProductId() {
        return sizeProductId;
    }

    public void setSizeProductId(int sizeProductId) {
        this.sizeProductId = sizeProductId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SizeProduct(Product product, String size, int quantity) {
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public SizeProduct() {
    }
}
