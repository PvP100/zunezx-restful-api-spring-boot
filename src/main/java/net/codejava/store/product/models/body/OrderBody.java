package net.codejava.store.product.models.body;

import java.util.List;

public class OrderBody {

    private String customerID;
    private String customerName;
    private String phoneNumber;
    private String address;
    private List<DetailBody> details;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<DetailBody> getDetails() {
        return details;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDetails(List<DetailBody> details) {
        this.details = details;
    }
}