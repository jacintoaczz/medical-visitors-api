package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.models.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
