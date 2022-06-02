package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Product;
import net.codejava.store.product.models.view.CategoryPreview;
import net.codejava.store.product.models.view.ProductPreview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Product, String>{
    Product findById(String clothesID);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c ")
    Page<ProductPreview> getAllClothesPreviews(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.isSale = :isSale")
    Page<ProductPreview> getAllProduct(Pageable pageable, @Param("isSale") int isSale);

    @Query("select new net.codejava.store.product.models.view.CategoryPreview(c) " +
            " from Product c ")
    Page<CategoryPreview> getSubCate(Pageable pageable);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.description = ?1")
    Page<ProductPreview> getProductBySubCate(Pageable pageable, String description);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = ?1")
    Page<ProductPreview> getProductByCategory(Pageable pageable, int id);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.brand.id = ?1")
    Page<ProductPreview> getProductByBrand(Pageable pageable, int id);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = :categoryId and c.brand.id = :brandId")
    Page<ProductPreview> getProductByCategoryAndBrand(Pageable pageable, @Param("categoryId") int categoryId, @Param("brandId") int brandId);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = :categoryId and c.brand.id = :brandId and c.isSale = :isSale")
    Page<ProductPreview> getSaleProductByCategoryAndBrand(Pageable pageable, @Param("categoryId") int categoryId, @Param("brandId") int brandId, @Param("isSale") int isSale);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.brand.id = :brandId and c.isSale = :isSale")
    Page<ProductPreview> getSaleProductByBrand(Pageable pageable, @Param("brandId") int brandId, @Param("isSale") int isSale);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) " +
            " from Product c where c.category.id = :categoryId and c.isSale = :isSale")
    Page<ProductPreview> getSaleProductByCategory(Pageable pageable, @Param("categoryId") int categoryId, @Param("isSale") int isSale);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) from Product c where c.category.id = ?1")
    Page<ProductPreview> getSimilarClothesPreviews(Pageable pageable, int categoryID);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(c) from Product c where c.id = ?1")
    ProductPreview getProductDetail(String productId);

    @Query("select p.id from Product p where p.name = ?1")
    String getProductByName(String name);

    @Query("select new net.codejava.store.product.models.view.ProductPreview(p) " +
            "from Product p where p.name like %?1%")
    Page<ProductPreview> searchByName(Pageable pageable,String name);

    long countByCategory(int description);

    @Query(value = "select product.description, SUM(product.quantity) " +
            " from product  GROUP BY product.description", nativeQuery = true)
    List<Object[]> countBySubCate();
}
