package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezra.programandojuntos.models.entity.Modulo;
import com.ezra.programandojuntos.models.entity.Role;

public interface IRoleDao extends JpaRepository<Role, Long> {
	

	@Query("from Modulo")
	public List<Modulo> findAllModulos();
	
}
