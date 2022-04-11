package net.codejava.store.product.models.view;

import java.util.List;

public class OrderDetailResultView {

    private String customerName;
    private String phone;
    private String address;
    private long totalPrice;
    private OrderPreview order;

    private List<OrderDetailView> listProduct;

    public OrderDetailResultView(String customerName, String phone, String address, OrderPreview order, List<OrderDetailView> listProduct, int totalPrice) {
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.order = order;
        this.listProduct = listProduct;
        this.totalPrice = totalPrice;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public OrderPreview getOrder() {
        return order;
    }

    public void setOrder(OrderPreview order) {
        this.order = order;
    }

    public List<OrderDetailView> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<OrderDetailView> listProduct) {
        this.listProduct = listProduct;
    }
}
