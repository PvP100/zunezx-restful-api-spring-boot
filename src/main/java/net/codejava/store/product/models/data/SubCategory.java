package net.codejava.store.product.models.data;

import javax.persistence.*;

@Entity
@Table(name = "SubCategory")
public class SubCategory {

    public static final String TITLE = "title";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "categoryId")
    private Category category;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int subCategoryId;
    private String title;

    public SubCategory() {
    }

    public SubCategory(Category category, String title) {
        this.category = category;
        this.title = title;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category categoryId) {
        this.category = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
