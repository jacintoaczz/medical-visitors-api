package com.example.controllers;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.example.models.Appointment;
import com.example.models.Association;
import com.example.models.Doctor;
import com.example.models.Hospital;
import com.example.models.Visitor;
import com.example.repositories.AppointmentRepository;
import com.example.repositories.AssociationRepository;
import com.example.repositories.DoctorRepository;
import com.example.repositories.HospitalRepository;
import com.example.repositories.VisitorRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

	@Autowired
	AppointmentRepository _appointmentRepository;
	@Autowired
	DoctorRepository _doctorRepository;
	@Autowired
	VisitorRepository _visitorRepository;

	@GetMapping("/all")
	public ResponseEntity<?> getAll() {
		try {
			List<Appointment> appointments = _appointmentRepository.findAll();

			return new ResponseEntity<>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createAppointment(@RequestBody Appointment body) {
		try {
			Optional<Doctor> _doctor = _doctorRepository.findById(body.getDoctor().getDoctorId());
			Appointment newAppointment = new Appointment();

			Calendar _calendar = Calendar.getInstance();
			_calendar.setTime(body.getDate());
			int day = _calendar.get(Calendar.DAY_OF_WEEK);

			if (day == _doctor.get().getHospital().getFreeDay()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
			}

			newAppointment.setDate(body.getDate());
			newAppointment.setTime(body.getTime());
			newAppointment.setDoctor(_doctor.get());
			newAppointment.setVisitor(body.getVisitor());
			newAppointment.setIsPending(true);
			newAppointment.setIsAccepted(false);

			Appointment _new = _appointmentRepository.save(newAppointment);
			return new ResponseEntity<>(_new, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: " + e);
			return null;
		}

	}

	@PutMapping("/accept/{id}/{visitorId}")
	public ResponseEntity<?> acceptAppointment(@PathVariable Long id, @PathVariable Long visitorId) {
		try {
			System.out.println("Holaaaa");
			Optional<Appointment> _appointment = _appointmentRepository.findById(id);
			Optional<Visitor> _visitor = _visitorRepository.findById(visitorId);
			if (_appointment.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Hospital hospital = _appointment.get().getDoctor().getHospital();

			System.out.println("Email: " + hospital.getEmail());
			Long count = _appointmentRepository.countVisitorsOnADate(_appointment.get().getDate());
			Long monthlyCount = _appointmentRepository.countVisitorsOnMonthByHospitalAndByCompany(hospital.getName(),
					_visitor.get().getCompany(), _appointment.get().getDate());

			System.out.println("Conteo de visitadores en el dia: " + count);
			System.out.println("Conteo de visitadores en el mes: " + monthlyCount);

			if (count == null) {
				count = (long) 0;
			}

			if (monthlyCount == null) {
				monthlyCount = (long) 0;
			}
			// There can't be more than 2 visitors on a hospital on the same day
			if (count == 2) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
			// A company can't visit a hospital more than 2 times per month
			if (monthlyCount == 2) {
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}

			Appointment appointment = _appointment.get();
			appointment.setIsPending(false);
			appointment.setIsAccepted(true);

			_appointmentRepository.save(appointment);
			return new ResponseEntity<>(appointment, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Excepcion: " + e);
			return null;
		}

	}

	@PutMapping("/reject/{id}")
	public ResponseEntity<?> rejectAppointment(@PathVariable Long id) {
		try {
			Optional<Appointment> _visitor = _appointmentRepository.findById(id);
			if (_visitor.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Appointment appointment = _visitor.get();
			appointment.setIsPending(false);
			appointment.setIsAccepted(false);

			_appointmentRepository.save(appointment);
			return new ResponseEntity<>(appointment, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

}
