package net.codejava.store.customer.dao;

import net.codejava.store.customer.models.data.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate,String> {
}
