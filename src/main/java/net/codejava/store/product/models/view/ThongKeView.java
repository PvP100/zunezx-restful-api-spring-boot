package net.codejava.store.product.models.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ThongKeView {
    private long totalProduct;
    private long totalOrder;
    private long orderChecked;
    private long orderUnchecked;
    private long orderCanceled;
    private int totalCategory;
    private int totalBrand;
    private List<StaticView> category;
    private List<StaticView> brand;
//    private Map<String, BigDecimal> listSubCate;

    public ThongKeView() {
    }

    public long getOrderCanceled() {
        return orderCanceled;
    }

    public void setOrderCanceled(long orderCanceled) {
        this.orderCanceled = orderCanceled;
    }

    public ThongKeView(long totalProduct, long totalOrder, long orderChecked, long orderUnchecked, long orderCanceled) {
        this.totalProduct = totalProduct;
        this.totalOrder = totalOrder;
        this.orderChecked = orderChecked;
        this.orderUnchecked = orderUnchecked;
        this.orderCanceled = orderCanceled;
    }

    public int getTotalCategory() {
        return totalCategory;
    }

    public void setTotalCategory(int totalCategory) {
        this.totalCategory = totalCategory;
    }

    public int getTotalBrand() {
        return totalBrand;
    }

    public void setTotalBrand(int totalBrand) {
        this.totalBrand = totalBrand;
    }

    public List<StaticView> getCategory() {
        return category;
    }

    public void setCategory(List<StaticView> category) {
        this.category = category;
    }

    public List<StaticView> getBrand() {
        return brand;
    }

    public void setBrand(List<StaticView> brand) {
        this.brand = brand;
    }

    public long getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(long totalProduct) {
        this.totalProduct = totalProduct;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public long getOrderChecked() {
        return orderChecked;
    }

    public void setOrderChecked(long orderChecked) {
        this.orderChecked = orderChecked;
    }

    public long getOrderUnchecked() {
        return orderUnchecked;
    }

    public void setOrderUnchecked(long orderUnchecked) {
        this.orderUnchecked = orderUnchecked;
    }

//    public Map<String, BigDecimal> getListSubCate() {
//        return listSubCate;
//    }
//
//    public void setListSubCate(Map<String, BigDecimal> listSubCate) {
//        this.listSubCate = listSubCate;
//    }
}
