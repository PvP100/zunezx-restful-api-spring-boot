package net.codejava.store.admin.dao;

import net.codejava.store.admin.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String>{
}
