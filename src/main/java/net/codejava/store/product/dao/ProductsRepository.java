package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.view.CategoryPreview;
import net.codejava.store.product.models.view.ProductPreview;
import net.codejava.store.product.models.view.ProductViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Product, String>{
    Product findById(String clothesID);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c ")
    Page<ProductPreview> getAllClothesPreviews(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.CategoryPreview(c) " +
            " from Product c ")
    Page<CategoryPreview> getSubCate(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.description = ?1")
    Page<ProductPreview> getProductBySubCate(Pageable pageable, String description);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = ?1")
    Page<ProductPreview> getProductByCategory(Pageable pageable, int id);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) from Product c where c.category.id = ?1")
    Page<ProductPreview> getSimilarClothesPreviews(Pageable pageable, int categoryID);

    @Query("select new net.codejava.store.product.models.view.ProductViewModel(c) from Product c where c.id = ?1")
    ProductViewModel getClothesViewModel(String clothesID);

    @Query("select p.id from Product p where p.name = ?1")
    String getProductByName(String name);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(p) " +
            "from Product p where p.name like %?1%")
    Page<ProductPreview> searchByName(Pageable pageable,String name);

    long countByDescription(String description);

    @Query(value = "select product.description, SUM(product.quantity) " +
            " from product  GROUP BY product.description", nativeQuery = true)
    List<Object[]> countBySubCate();
}
