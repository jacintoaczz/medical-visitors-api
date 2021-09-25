package com.example.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "visitors")
public class Visitor {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "company")
	private String company;

	@Column(name = "password")
	private String password;

	@Column(name = "is_paid")
	private Boolean isPaid;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "visitor", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("visitor")
	private List<Appointment> appointmentList = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "Visitor [id=" + id + ", name=" + name + ", lastName=" + lastName + ", email=" + email + ", company="
				+ company + ", password=" + password + ", isPaid=" + isPaid + ", isActive=" + isActive
				+ ", appointmentList=" + appointmentList + "]";
	}

}
