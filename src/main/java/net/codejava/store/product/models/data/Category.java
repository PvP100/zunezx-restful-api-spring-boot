package net.codejava.store.product.models.data;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String title;
    private String imgUrl;
    private int categoryTotalCount = 0;

    public int getCategoryTotalCount() {
        return categoryTotalCount;
    }

    public void setCategoryTotalCount(int categoryTotalCount) {
        this.categoryTotalCount = categoryTotalCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

