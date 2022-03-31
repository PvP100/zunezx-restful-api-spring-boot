package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.view.CategoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c ")
    List<CategoryView> getAllCategory();

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c ")
    List<CategoryView> getCategoryCount();

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c where c.categoryType = ?1")
    List<CategoryView> checkCategoryType(String categoryType);

}
