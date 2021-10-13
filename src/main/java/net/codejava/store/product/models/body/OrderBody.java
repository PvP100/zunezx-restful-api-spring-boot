package net.codejava.store.product.models.body;

import java.util.List;

public class OrderBody {

    private String customerName;
    private String phone;
    private String address;
    private List<DetailBody> details;
    private double total;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public List<DetailBody> getDetails() {
        return details;
    }

    public void setDetails(List<DetailBody> details) {
        this.details = details;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
