package net.codejava.store.product.models.view;

import java.math.BigDecimal;
import java.util.Map;

public class ThongKeView {
    private long totalProduct;
    private Double totalInCome;
    private long totalOrder;
    private long orderChecked;
    private long orderUnchecked;
    private Map<String, BigDecimal> listSubCate;

    public ThongKeView() {
    }

    public ThongKeView(long totalProduct, Double totalInCome, long totalOrder, long orderChecked, long orderUnchecked, Map<String, BigDecimal> listSubCate) {
        this.totalProduct = totalProduct;
        this.totalInCome = totalInCome;
        this.totalOrder = totalOrder;
        this.orderChecked = orderChecked;
        this.orderUnchecked = orderUnchecked;
        this.listSubCate = listSubCate;
    }

    public long getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(long totalProduct) {
        this.totalProduct = totalProduct;
    }

    public Double getTotalInCome() {
        return totalInCome;
    }

    public void setTotalInCome(Double totalInCome) {
        this.totalInCome = totalInCome;
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

    public Map<String, BigDecimal> getListSubCate() {
        return listSubCate;
    }

    public void setListSubCate(Map<String, BigDecimal> listSubCate) {
        this.listSubCate = listSubCate;
    }
}
