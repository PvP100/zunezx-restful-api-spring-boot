package net.codejava.store.customer.dao;


import net.codejava.store.customer.models.view.SaveClothesPreview;
import net.codejava.store.product.models.data.SaveClothes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface SaveClothesRepository extends JpaRepository<SaveClothes, String>{

    @Transactional
    @Modifying
    int deleteByCustomer_idAndProduct_Id(String customerID,String clothesID);


    @Query("select new net.codejava.store.customer.models.view.SaveClothesPreview(sc) " +
            "from SaveClothes sc" +
            " where sc.customer.id = ?1")
    Page<SaveClothesPreview> getAllSavedClothes(String customerID, Pageable pageable);

    boolean existsByCustomer_IdAndProduct_Id(String customerID, String clothesID);

}
