package net.codejava.store.customer.models.data;

import net.codejava.store.auth.models.User;
import net.codejava.store.auth.models.body.FacebookLoginBody;
import net.codejava.store.customer.models.body.ProfileBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String fullName;
    private String phone;
    private int gender;
    private String address;
    private String identityCard;
    private String description;
    private String avatarUrl;
    private Date birthday;
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private User user;

    public String getEmail() {
        return email;
    }

    public void update(ProfileBody body) {
        this.fullName = body.getFullName();
        this.phone = body.getPhone();
        this.address = body.getAddress();
        this.identityCard = body.getIdentityCard();
        this.avatarUrl = body.getAvatarUrl();
        this.gender = body.getGender();
        if (body.getBirthday() == -1) {
            birthday = new Date();
        } else {
            birthday = new Date(body.getBirthday());
        }
        this.email = body.getEmail();
    }

    public Customer() {
    }

    public Customer(FacebookLoginBody facebookLoginBody) {
        setFullName(facebookLoginBody.getFullname());
        if (facebookLoginBody.getBirthDay() != -1) {
            setBirthday(new Date(facebookLoginBody.getBirthDay()));
        } else {
            setBirthday(new Date());
        }
        setEmail(facebookLoginBody.getEmail());
        setAvatarUrl(facebookLoginBody.getAvatarUrl());
        setEmail(facebookLoginBody.getEmail());
        setGender(facebookLoginBody.getGender() == true ? 1 : 0);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
