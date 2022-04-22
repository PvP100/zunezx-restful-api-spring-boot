package net.codejava.store.product.models.data;

import net.codejava.store.product.models.body.DetailBody;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    public static final String ID = "id";

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name= "orderId")
    private Order order;

    @ElementCollection
    private List<Product> productList;
    private double total;
    private int quantityS;
    private int quantityM;
    private int quantityL;
    private int quantityXL;

    public OrderDetail() {
    }

    public OrderDetail(List<Product> productList, double total, int quantityS, int quantityM, int quantityL, int quantityXL) {
        this.productList = productList;
        this.total = total;
        this.quantityS = quantityS;
        this.quantityM = quantityM;
        this.quantityL = quantityL;
        this.quantityXL = quantityXL;
    }

    public double calculateTotal() {
        double totalCount = 0;
        for (int i = 0; i < productList.size(); i++) {
            totalCount += (quantityS + quantityM + quantityL + quantityXL) * productList.get(i).getPrice();
        }
        return totalCount;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantityS() {
        return quantityS;
    }

    public void setQuantityS(int quantityS) {
        this.quantityS = quantityS;
    }

    public int getQuantityM() {
        return quantityM;
    }

    public void setQuantityM(int quantityM) {
        this.quantityM = quantityM;
    }

    public int getQuantityL() {
        return quantityL;
    }

    public void setQuantityL(int quantityL) {
        this.quantityL = quantityL;
    }

    public int getQuantityXL() {
        return quantityXL;
    }

    public void setQuantityXL(int quantityXL) {
        this.quantityXL = quantityXL;
    }
}
