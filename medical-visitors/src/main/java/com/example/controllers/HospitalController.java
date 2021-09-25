package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Association;
import com.example.models.Doctor;
import com.example.models.Hospital;
import com.example.models.Visitor;
import com.example.repositories.AssociationRepository;
import com.example.repositories.HospitalRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

	@Autowired
	HospitalRepository _hospitalRepository;

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			List<Hospital> hospitals = _hospitalRepository.findAll();

			return new ResponseEntity<>(hospitals, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		try {
			Optional<Hospital> hospitals = _hospitalRepository.findById(id);

			return new ResponseEntity<>(hospitals, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> hospitalLogin(@RequestBody Hospital body) {
		try {
			Optional<Hospital> _hospital = _hospitalRepository.findByEmail(body.getEmail());
			if (_hospital.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Hospital hospital = _hospital.get();
			if (hospital.getPassword().equals(body.getPassword())) {
				return new ResponseEntity<>(hospital, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@PostMapping("/create")
	public ResponseEntity<?> createHospital(@RequestBody Hospital body) {
		try {
			List<Doctor> doctorsList = new ArrayList<>();

			Optional<Hospital> hospital = _hospitalRepository.findByEmail(body.getEmail());
			if (!hospital.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			// We create a new hospital to save in the database
			Hospital newHospital = new Hospital();
			newHospital.setName(body.getName());
			newHospital.setAddress(body.getAddress());
			newHospital.setEmail(body.getEmail());
			newHospital.setPassword(body.getPassword());
			newHospital.setFreeDay(body.getFreeDay());

			// Now we add all the doctors
			List<Doctor> doctorsArray = body.getDoctorList();
			for (Doctor doctor : doctorsArray) {
				Doctor newDoctor = new Doctor();
				newDoctor.setName(doctor.getName());
				newDoctor.setLastName(doctor.getLastName());
				newDoctor.setHospital(newHospital);

				doctorsList.add(newDoctor);
			}

			newHospital.setDoctorList(doctorsList);

			Hospital savedHospital = _hospitalRepository.save(newHospital);
			return new ResponseEntity<>(savedHospital, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
}
