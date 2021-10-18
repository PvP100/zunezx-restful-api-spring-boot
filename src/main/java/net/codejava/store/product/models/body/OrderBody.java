package net.codejava.store.product.models.body;

import java.util.List;

public class OrderBody {

    private String customerID;
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

    public void setDetails(List<DetailBody> details) {
        this.details = details;
    }
}