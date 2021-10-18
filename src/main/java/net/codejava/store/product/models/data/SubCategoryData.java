package net.codejava.store.product.models.data;

public class SubCategoryData {
    private String title;
    private int amount;

    public SubCategoryData() {
    }

    public SubCategoryData(String title, int amount) {
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
