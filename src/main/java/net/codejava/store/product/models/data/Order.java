package net.codejava.store.product.models.data;

import net.codejava.store.product.models.body.OrderBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
    public static final String CREATE_AT = "createAt";

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    private String customerName;
    private String phone;
    private String address;
    private Date createAt;
    private Date updateAt;
    private double total;
    private int isCheck;

    public Order() {
    }

    public void addOrder(OrderBody body){
        this.customerName = body.getCustomerName();
        this.phone = body.getPhone();
        this.address = body.getAddress();
        this.total = body.getTotal();
        this.createAt = new Date();
        this.updateAt = new Date();
        this.isCheck = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }
}
