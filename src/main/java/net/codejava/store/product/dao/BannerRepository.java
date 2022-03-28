package net.codejava.store.product.dao;

import net.codejava.store.product.models.data.Banner;
import net.codejava.store.product.models.view.BannerView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, String> {

    @Query("select new net.codejava.store.product.models.view.BannerView(b) " +
            " from Banner b ")
    List<BannerView> getBanner();

}
