package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.Association;
import java.util.Optional;

public interface AssociationRepository extends JpaRepository<Association, Long> {
	Optional<Association> findByEmail(String email);
}
