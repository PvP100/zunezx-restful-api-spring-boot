//package net.codejava.store.product.models.data;
//
//import net.codejava.store.customer.models.data.Customer;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "save_clothes")
//public class SaveClothes {
//    public final static String SAVED_DATE = "saveDate";
//    public final static String CLOTHES = "CLOTHES";
//    @Id
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @GeneratedValue(generator = "uuid")
//    private String id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "clothesID")
//    private Product product;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "customerID")
//    private Customer customer;
//    private Date saveDate;
//
//    public SaveClothes() {
//    }
//
//    public SaveClothes(Product product, Customer customer) {
//        this.product = product;
//        this.customer = customer;
//        this.saveDate= new Date();
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public Product getClothes() {
//        return product;
//    }
//
//    public void setClothes(Product clothes) {
//        this.product = product;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Date getSaveDate() {
//        return saveDate;
//    }
//
//    public void setSaveDate(Date saveDate) {
//        this.saveDate = saveDate;
//    }
//}
