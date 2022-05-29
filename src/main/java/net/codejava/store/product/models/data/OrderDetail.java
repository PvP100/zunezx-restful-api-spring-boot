package net.codejava.store.product.models.data;

import net.codejava.store.product.models.body.DetailBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderDetail {
    public static final String ID = "id";

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name= "OrderId")
    private Order order;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name= "ProductId")
    private Product product;

    private int quantity;
    private double price;
    private double total;

    public OrderDetail() {
    }

    public void addDetail(DetailBody body) {
        this.quantity = body.getQuantity();
        this.price = product.getPrice();
        this.total = (double) quantity * price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
