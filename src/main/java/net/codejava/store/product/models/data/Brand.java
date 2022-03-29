package net.codejava.store.product.models.data;

import javax.persistence.*;

@Entity
@Table(name = "Brand")
public class Brand {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String imgUrl;

    public Brand() {
    }

    public Brand(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
