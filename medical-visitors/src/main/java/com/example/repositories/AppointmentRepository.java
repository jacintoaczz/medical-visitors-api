package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.models.*;

import java.util.Date;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	Optional<Appointment> findById(long id);

	@Query(value = "SELECT COUNT(visitors.id) as num_appointments FROM appointments INNER JOIN visitors ON visitors.id = appointments.visitor_id INNER JOIN doctors ON doctors.doctor_id = appointments.doctor_id INNER JOIN hospitals ON hospitals.id = doctors.hospital_id WHERE hospitals.name = ?1 AND visitors.company = ?2 AND date_format(appointments.date, '%Y-%m') = date_format(?3, '%Y-%m') AND appointments.is_accepted = true AND visitors.is_deleted = false group by date_format(appointments.date, '%Y-%m')", nativeQuery = true)
	Long countVisitorsOnMonthByHospitalAndByCompany(String name, String companyName, Date date);

	@Query(value = "SELECT COUNT(visitors.id) as num_visitors FROM appointments\r\n"
			+ "INNER JOIN visitors ON visitors.id = appointments.visitor_id\r\n"
			+ "INNER JOIN doctors ON doctors.doctor_id = appointments.doctor_id\r\n"
			+ "INNER JOIN hospitals ON hospitals.id = doctors.hospital_id\r\n"
			+ " WHERE date_format(appointments.date, '%Y-%m-%d') = date_format(:date, '%Y-%m-%d') AND appointments.is_accepted = true \r\n"
			+ " group by date_format(appointments.date, '%Y-%m-%d')", nativeQuery = true)
	Long countVisitorsOnADate(Date date);
}
