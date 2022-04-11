package net.codejava.store.product.models.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Banner")
public class Banner {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String imgUrl;

    public Banner() {
    }

    public Banner(int id, String imgUrl) {
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
