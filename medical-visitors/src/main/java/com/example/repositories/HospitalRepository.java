package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.models.Hospital;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	Optional<Hospital> findByEmail(String email);

	Optional<Hospital> findByEmailAndIsDeleted(String email, Boolean isDeleted);

	Optional<Hospital> findByName(String name);

	Optional<Hospital> findById(long id);

	@Query(value = "SELECT * from hospitals WHERE is_deleted = false", nativeQuery = true)
	List<Hospital> findAllValidHospitals();

	@Query(value = "SELECT * from hospitals WHERE name = ?1 AND is_deleted = false", nativeQuery = true)
	List<Hospital> findValidHospitalsByName(String name);

	@Query(value = "SELECT * from hospitals WHERE email = ?1 AND is_deleted = false", nativeQuery = true)
	List<Hospital> findValidHospitalsByEmail(String email);
}
