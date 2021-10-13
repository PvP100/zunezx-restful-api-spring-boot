package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.view.ProductPreview;
import net.codejava.store.product.models.view.ProductViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductsRepository extends JpaRepository<Product, String>{
    Product findById(String clothesID);

    @Query("select new net.codejava.store.product.models.view.ClothesPreview(c) " +
            " from Clothes c ")
    Page<ProductPreview> getAllClothesPreviews(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.ClothesPreview(c) from Clothes c where c.category.id = ?1")
    Page<ProductPreview> getSimilarClothesPreviews(Pageable pageable, String categoryID);

    @Query("select new net.codejava.store.product.models.view.ClothesViewModel(c) from Clothes c where c.id = ?1")
    ProductViewModel getClothesViewModel(String clothesID);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = ?1")
    Page<ProductPreview> getProductByCategory(Pageable pageable, String id);

}
