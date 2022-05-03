package net.codejava.store.product.models.data;


import net.codejava.store.product.models.body.ProductBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    public static final String CREATED_DATE = "createdDate";
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private long price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Date createdDate;
    private String avatarUrl;
    private int isSale = 0;
    private long salePrice = 0;
    private int quantity;
    private double salePercent;
    private String warranty;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name= "categoryID")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name= "brandId")
    private Brand brand;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public Product() {
        this.createdDate = new Date();
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Product(ProductBody body) {
        this.name = body.getName();
        this.price = body.getPrice();
        this.description = body.getDescription();
        this.createdDate = new Date();
        this.isSale = 0;
        this.quantity = 0;
        this.salePercent = 0;
    }

    public Product(String name, long price, String description, int quantity, int isSale, String warranty) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.createdDate = new Date();
        this.quantity = quantity;
        this.isSale = isSale;
        this.warranty = warranty;
    }

    public void update(ProductBody body){
        this.name = body.getName();
        this.price = body.getPrice();
        this.description = body.getDescription();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }

    public double getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(long salePercent) {
        this.salePercent = salePercent;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
