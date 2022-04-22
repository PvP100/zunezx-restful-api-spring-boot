package net.codejava.store.customer.models.data;

import net.codejava.store.customer.models.body.OrderBody;
import net.codejava.store.customer.models.body.SetOrderBody;
import net.codejava.store.product.models.data.Product;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {
    public static final String CREATED_DATE = "creatDate";
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    @OneToOne
    @JoinColumn(name = "productID")
    private Product product;
    @OneToOne
    @JoinColumn(name = "customerID")
    private Customer customer;
    private String color;
    private String size;
    private int amount;
    private int price;
    private Date creatDate;

    public CustomerOrder() {
    }

    public CustomerOrder(Product product, Customer customer, OrderBody orderBody) {
        setColor(orderBody.getColor());
        setAmount(orderBody.getAmount());
        setClothes(product);
        setCustomer(customer);
        setSize(orderBody.getSize());
        setCreatDate(new Date());
        setPrice(orderBody.getPrice());
    }
    public CustomerOrder(Product product, Customer customer, SetOrderBody orderBody) {
        setColor(orderBody.getColor());
        setClothes(product);
        setAmount(orderBody.getAmount());
        setCustomer(customer);
        setSize(orderBody.getSize());
        setCreatDate(new Date());
        setPrice(orderBody.getPrice());
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getClothes() {
        return product;
    }

    public void setClothes(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }
}
