package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.view.CategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c ")
    Page<CategoryView> getAllCategory(Pageable pageable);

}
