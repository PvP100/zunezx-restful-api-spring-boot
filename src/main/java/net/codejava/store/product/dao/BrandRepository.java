package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Brand;
import net.codejava.store.product.models.view.BrandView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("select new net.codejava.store.product.models.view.BrandView(b) " +
            " from Brand b ")
    List<BrandView> getBrand();

}
