package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Category;
import net.codejava.store.product.models.view.CategoryView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c ")
    List<CategoryView> getAllCategory();

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            " from Category c ")
    List<CategoryView> getCategoryCount();

    @Transactional
    @Modifying
    @Query("update Category c set c.categoryTotalCount = c.categoryTotalCount + 1 WHERE c.id = :id")
    void plusTotal(@Param("id") int id);

    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
            "from Category c where c.title like %?1%")
    Page<CategoryView> searchCategoryByName(Pageable pageable, String name);

//    @Query("select new net.codejava.store.product.models.view.CategoryView(c) " +
//            " from Category c where c.categoryType = ?1")
//    List<CategoryView> checkCategoryType(String categoryType);

}
