package com.example.models;
import javax.persistence.*;

@Entity
@Table(name = "associations")
public class Association {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Association [id=" + id + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
