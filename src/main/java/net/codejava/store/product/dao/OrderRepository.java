package net.codejava.store.product.dao;

import net.codejava.store.customer.models.data.CustomerOrder;
import net.codejava.store.customer.models.view.CustomerOrderPreview;
import net.codejava.store.product.models.data.Order;
import net.codejava.store.product.models.view.OrderPreview;
import net.codejava.store.product.models.view.ProductPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o ")
    Page<OrderPreview> getAllOrderPreview(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o) " +
            "from Order o where o.id = :id")
    Page<OrderPreview> searchByOrderId(Pageable pageable, @Param("id") int id);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o) " +
            "from Order o where o.id = :id and o.isCheck = :isCheck")
    Page<OrderPreview> searchByOrderIdAndStatus(Pageable pageable, @Param("id") int id, @Param("isCheck") int isCheck);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.customer.id = ?1")
    Page<OrderPreview> getCustomerOrder(String customerid, Pageable pageable);

    long countByIsCheck(int isCheck);

    @Query(value = "select sum(o.total) from orders as o where o.isCheck = 0",nativeQuery = true)
    Double totalIncome();

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.isCheck = 1")
    Page<OrderPreview> getOrderChecked(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.isCheck = 0")
    Page<OrderPreview> getOrderUnchecked(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.isCheck = -1")
    Page<OrderPreview> getOrderCanceled(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.customer.id = ?1 and o.isCheck = 1")
    List<OrderPreview> getOrderCheckedByCustomerId(String id);

    @Query(value = "select COALESCE(sum(total), 0) from orders where isCheck = 1", nativeQuery = true)
    long getTotalPrice();

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.customer.id = ?1 and o.isCheck = 0")
    List<OrderPreview> getOrderUncheckedByCustomerId(String id);

    @Query("select new net.codejava.store.product.models.view.OrderPreview(o)" +
            " from Order o where o.customer.id = ?1 and o.isCheck = -1")
    List<OrderPreview> getOrderCanceledByCustomerId(String id);
}
