package net.codejava.store.customer.models.view;


import net.codejava.store.customer.models.data.CustomerOrder;

public class CustomerOrderPreview {
    private String id;
    private String name;
    private double price;
    private String logoUrl;
    private long createdDate;
    private int amount;

    public CustomerOrderPreview(CustomerOrder order) {
        setId(order.getClothes().getId());
        setName(order.getClothes().getName());
        setPrice(order.getClothes().getPrice());
        setLogoUrl(order.getClothes().getAvatarUrl());
        setCreatedDate(order.getCreatDate().getTime());
        setAmount(order.getAmount());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
