package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.RateClothes;
import net.codejava.store.product.models.view.RateClothesViewModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateClothesRepository extends JpaRepository<RateClothes, String>{
    boolean existsByCustomerIdAndClothesId(String customerID, String clothesID);

    @Query("select new net.codejava.store.product.models.view.RateClothesViewModel(r) " +
            "from RateClothes r where r.product.id = ?1")
    List<RateClothesViewModel> getAllRate(String clothesID, Sort pageable);

    RateClothes findByClothes_IdAndCustomer_Id(String clothesID,String customerID);
//    @Transactional
//    @Modifying
//    @Query("update RateClothes r set r.message = ?1, r.rating = ?2,r.rateDate = ?3 where r.")
//    void updateRateClothes(String message, int rating);
}
