package com.example.controllers;

import java.util.Optional;
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

	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			List<Visitor> visitors = _visitorRepository.findAll();

			return new ResponseEntity<>(visitors, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createVisitor(@RequestBody Visitor body) {
		try {
			Visitor _visitor = new Visitor();

			Optional<Visitor> visitor = _visitorRepository.findByEmail(body.getEmail());
			if (!visitor.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			}

			_visitor.setName(body.getName());
			_visitor.setLastName(body.getLastName());
			_visitor.setEmail(body.getEmail());
			_visitor.setCompany(body.getCompany());
			_visitor.setPassword(body.getPassword());
			_visitor.setIsPaid(false);
			_visitor.setIsActive(false);

			_visitorRepository.save(_visitor);

			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

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

	@PutMapping("/set-status/{id}")
	public ResponseEntity<?> setActiveStatus(@PathVariable Long id) {
		try {
			Optional<Visitor> _visitor = _visitorRepository.findById(id);
			if (_visitor.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			Visitor visitor = _visitor.get();
			visitor.setIsActive(true);

			_visitorRepository.save(visitor);
			return new ResponseEntity<>(visitor, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}
}
