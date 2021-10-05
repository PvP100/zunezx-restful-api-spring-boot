package net.codejava.store.admin.dao;

import net.codejava.store.admin.models.ApplicationVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion,String> {
}
