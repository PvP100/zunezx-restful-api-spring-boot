package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.SubCategory;
import net.codejava.store.product.models.view.CategoryView;
import net.codejava.store.product.models.view.SubCategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("select new net.codejava.store.product.models.view.SubCategoryView(c) " +
            " from SubCategory c ")
    Page<SubCategoryView> getAllSubCategory(Pageable pageable);

}
