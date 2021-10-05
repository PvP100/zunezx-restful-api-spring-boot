package net.codejava.store.customer.dao;

import net.codejava.store.customer.models.data.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,String> {
}
