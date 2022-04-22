package net.codejava.store.product.models.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ProductCover")
public class ProductCover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "productId")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public ProductCover(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public ProductCover(String url) {
        this.url = url;
    }

    public ProductCover() {
    }
}
