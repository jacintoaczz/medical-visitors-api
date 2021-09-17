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
import com.example.models.Visitor;
import com.example.repositories.AssociationRepository;
import com.example.repositories.VisitorRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

	@Autowired
	AssociationRepository _associationRepository;
	@Autowired
	VisitorRepository _visitorRepository;

	@PostMapping("/login")
	public ResponseEntity<?> visitorLogin(@RequestBody Visitor body) {
		try {
			Optional<Visitor> _visitor = _visitorRepository.findByEmail(body.getEmail());
			if (_visitor.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Visitor visitor = _visitor.get();
			if (visitor.getPassword().equals(body.getPassword())) {
				return new ResponseEntity<>(visitor, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
}