package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.*;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	Optional<Appointment> findById(long id);
}
