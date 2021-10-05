package net.codejava.store.product.dao;

import net.codejava.store.customer.models.data.Order;
import net.codejava.store.customer.models.view.OrderPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,String> {
    @Query("select new net.codejava.store.customer.models.view.OrderPreview(o)" +
            " from Order o " +
            "where o.customer.id = ?1")
    Page<OrderPreview> getAllOrderPreview(String customerID, Pageable pageable);
}
