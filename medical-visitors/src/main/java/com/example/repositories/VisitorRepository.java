package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.models.Hospital;
import com.example.models.Visitor;

import java.util.List;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
	Optional<Visitor> findByEmail(String email);

	Optional<Visitor> findByEmailAndIsDeleted(String email, Boolean isDeleted);

	Optional<Visitor> findById(Long id);

	@Query(value = "SELECT * from visitors WHERE is_deleted = false", nativeQuery = true)
	List<Visitor> findAllValidVisitors();

	List<Visitor> findAll();
}
