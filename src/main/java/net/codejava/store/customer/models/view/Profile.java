package net.codejava.store.customer.models.view;


import net.codejava.store.customer.models.data.Customer;
import net.codejava.store.product.models.view.OrderStaticView;

public class Profile {
    private String id;
    private String fullName;
    private String phone;
    private String address;
    private String avatarUrl;
    private int gender;
    private String birthday;
    private String email;
    private OrderStaticView orderStaticView;

    public Profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile(Customer customer) {
        this.id = customer.getId();
        this.fullName = customer.getFullName();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
        this.avatarUrl = customer.getAvatarUrl();
        this.gender = customer.getGender();
        this.birthday = customer.getBirthday();
//        if (customer.getBirthday() == null) {
//            this.birthday = -1;
//        } else {
//            this.birthday = customer.getBirthday().getTime();
//
//        }
        this.email = customer.getEmail();
    }

    public OrderStaticView getOrderStaticView() {
        return orderStaticView;
    }

    public void setOrderStaticView(OrderStaticView orderStaticView) {
        this.orderStaticView = orderStaticView;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}