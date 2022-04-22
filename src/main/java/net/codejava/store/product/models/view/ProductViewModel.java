package net.codejava.store.product.models.view;


import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.data.ProductCover;
import net.codejava.store.product.models.data.RateClothes;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductViewModel {
    private String id;
    private String name;
    private double price;
    private String description;
    private Date createdDate;
    private String avatarUrl;
    private List<String> coverList;
//    private String cover1Url;
//    private String cover2Url;
//    private String cover3Url;
    private String subCategoryTitle;
    private String categoryID;
    private String size;
    private int quantity;
    private int numberSave;
    private boolean isSaved;
    private float avarageOfRate = 0;

    public ProductViewModel() {
    }

    public ProductViewModel(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.createdDate = product.getCreatedDate();
        this.avatarUrl = product.getAvatarUrl();
        this.coverList = product.getCoverUrl();
        this.subCategoryTitle = product.getSubCategory().getTitle();
        this.categoryID = product.getSubCategory().getCategory().getId();
//        this.quantity = product.getQuantity();
        this.isSaved = false;
        this.numberSave = product.getTotalSave();
//        this.size = product.getSize();
        setAvarageOfRate(getAvarageOfRate(product));
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAvarageOfRate() {
        return avarageOfRate;
    }

    public float getAvarageOfRate(Product product) {
        if(product.getRateClothes().size()==0){
            return 0;
        }
        int sum = 0;
        for (RateClothes rateClothes : product.getRateClothes()) {
            sum += rateClothes.getRating();
        }

        return (float) sum / product.getRateClothes().size();
    }

    public List<String> getCoverList() {
        return coverList;
    }

    public void setCoverList(List<String> coverList) {
        this.coverList = coverList;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setAvarageOfRate(float avarageOfRate) {
        this.avarageOfRate = avarageOfRate;
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

    public int getNumberSave() {
        return numberSave;
    }

    public void setNumberSave(int numberSave) {
        this.numberSave = numberSave;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean saved) {
        isSaved = saved;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getSubCategoryTitle() {
        return subCategoryTitle;
    }

    public void setSubCategoryTitle(String subCategoryTitle) {
        this.subCategoryTitle = subCategoryTitle;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

}
