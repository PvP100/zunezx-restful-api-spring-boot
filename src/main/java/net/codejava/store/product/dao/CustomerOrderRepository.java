package net.codejava.store.product.dao;

import net.codejava.store.customer.models.data.CustomerOrder;
import net.codejava.store.customer.models.view.CustomerOrderPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,String> {
    @Query("select new net.codejava.store.customer.models.view.CustomerOrderPreview(o)" +
            " from CustomerOrder o " +
            "where o.customer.id = ?1")
    Page<CustomerOrderPreview> getAllOrderPreview(String customerID, Pageable pageable);



}
