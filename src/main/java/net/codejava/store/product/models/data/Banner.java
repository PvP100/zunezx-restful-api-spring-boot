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
    private String linkUrl;

    public Banner() {
    }

    public Banner(int id, String imgUrl, String linkUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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
