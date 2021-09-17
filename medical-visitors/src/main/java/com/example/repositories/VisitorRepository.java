package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.Visitor;
import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
	Optional<Visitor> findByEmail(String email);
}
