package com.example.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.example.models.Association;
import com.example.repositories.AssociationRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/association")
public class AssociationController {

	@Autowired
	AssociationRepository _associationRepository;

	@PostMapping("/login")
	public ResponseEntity<?> associationLogin(@RequestBody Association body) {
		try {
			Optional<Association> _association = _associationRepository.findByEmail(body.getEmail());
			if (_association.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Association association = _association.get();
			if (association.getPassword().equals(body.getPassword())) {
				return new ResponseEntity<>(association, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
}
