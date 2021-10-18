package net.codejava.store.product.models.data;

public class NumProductBySubCateData {
    private String title;
    private int amount;

    public NumProductBySubCateData() {
    }

    public NumProductBySubCateData(String title, int amount) {
        this.title = title;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
