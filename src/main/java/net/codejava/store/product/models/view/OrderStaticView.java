package net.codejava.store.product.models.view;

public class OrderStaticView {

    private long uncheck = 0;
    private long cancel = 0;
    private long success = 0;

    public OrderStaticView(long uncheck, long cancel, long success) {
        this.uncheck = uncheck;
        this.cancel = cancel;
        this.success = success;
    }

    public long getUncheck() {
        return uncheck;
    }

    public void setUncheck(long uncheck) {
        this.uncheck = uncheck;
    }

    public long getCancel() {
        return cancel;
    }

    public void setCancel(long cancel) {
        this.cancel = cancel;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }
}
