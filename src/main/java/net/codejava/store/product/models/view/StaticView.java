package net.codejava.store.product.models.view;

public class StaticView {

    private int index;
    private String title;
    private long total;

    public StaticView(int index, String title, long total) {
        this.title = title;
        this.total = total;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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
