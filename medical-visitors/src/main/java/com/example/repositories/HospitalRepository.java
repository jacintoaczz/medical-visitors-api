package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.Hospital;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	Optional<Hospital> findByEmail(String email);
}
