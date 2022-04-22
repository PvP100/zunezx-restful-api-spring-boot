package net.codejava.store.product.models.view;

public class StaticView {

    private String title;
    private long total;

    public StaticView(String title, long total) {
        this.title = title;
        this.total = total;
    }

//    public StaticView() {
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
