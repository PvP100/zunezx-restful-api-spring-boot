package net.codejava.store.product.models.body;

public class UpdateSubCategoryBody {

    private Integer id;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UpdateSubCategoryBody(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public UpdateSubCategoryBody() {
    }
}
