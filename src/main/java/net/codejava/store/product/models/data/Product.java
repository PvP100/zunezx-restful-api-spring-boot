package net.codejava.store.product.models.data;


import net.codejava.store.product.models.body.ClothesBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private double price;
    private String description;
    private Date createdDate;
    private String avatarUrl;
    @ElementCollection
    private List<String> coverUrl;

    @ElementCollection
    private List<SizeProduct> size;
    private int isSale;
    private float salePercent;
    private int totalSave;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subCategoryId")
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<RateClothes> rateClothes;

    public Product() {
        this.createdDate = new Date();
    }

    public Product(ClothesBody body) {
        this.name = body.getName();
        this.price = body.getPrice();
        this.description = body.getDescription();
        this.createdDate = new Date();
        this.totalSave = 0;
        this.isSale = 0;
        this.salePercent = 0;
        this.size = body.getSize();
    }

    public Product(String name, double price, String description, List<SizeProduct> size) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.size = size;
        this.createdDate = new Date();
    }

    public void update(ClothesBody body) {
        this.name = body.getName();
        this.price = body.getPrice();
        this.description = body.getDescription();
    }

    public List<String> getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(List<String> coverUrl) {
        this.coverUrl = coverUrl;
    }

    //    public String getCover1Url() {
//        return cover1Url;
//    }
//
//    public void setCover1Url(String cover1Url) {
//        this.cover1Url = cover1Url;
//    }
//
//    public String getCover2Url() {
//        return cover2Url;
//    }
//
//    public void setCover2Url(String cover2Url) {
//        this.cover2Url = cover2Url;
//    }
//
//    public String getCover3Url() {
//        return cover3Url;
//    }
//
//    public void setCover3Url(String cover3Url) {
//        this.cover3Url = cover3Url;
//    }

    public List<SizeProduct> getSize() {
        return size;
    }

    public void setSize(List<SizeProduct> size) {
        this.size = size;
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

    public float getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(float salePercent) {
        this.salePercent = salePercent;
    }

    public Set<RateClothes> getRateClothes() {
        return rateClothes;
    }

    public void setRateClothes(Set<RateClothes> rateClothes) {
        this.rateClothes = rateClothes;
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

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public int getTotalSave() {
        return totalSave;
    }

    public void addSave() {
        totalSave++;
    }

    public void subSave() {
        totalSave--;
    }

    public void setTotalSave(int totalSave) {
        this.totalSave = totalSave;
    }
}
