package zw.co.rental.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zw.co.rental.app.entity.Property;
import zw.co.rental.app.entity.RentalStatus;
import zw.co.rental.app.entity.User;
import zw.co.rental.app.repository.PropertyRepository;
import zw.co.rental.app.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/properties")
public class PropertyController {

	@Autowired
	private PropertyRepository propertyRepo;

	@Autowired
	private UserRepository userRepo;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Property> getAllProperties() {
		return propertyRepo.findAll();
	}

	@GetMapping(value = "/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Property> getAllUserProperty(@PathVariable("id") int id) {
		return propertyRepo.findAllByUser_Id(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Property saveProperty(@RequestBody Property property, Authentication authentication) {

		User user = userRepo.findById(property.getUser().getId()).get();

		property.setUser(user);
		property.setStatus(RentalStatus.Available);
		property.setCreatedAt(LocalDateTime.now());

		return propertyRepo.save(property);
	}

	@GetMapping(value = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Property getProperty(@PathVariable("propertyId") int propertyId) {
		return propertyRepo.findById(propertyId).get();

	}

	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Property updateProperty(@PathVariable("id") int id, @RequestBody Property property,
			Authentication authentication) {

		User user = userRepo.findById(property.getUser().getId()).get();

		property.setId(property.getId());
		property.setUser(user);
		property.setStatus(property.getStatus());
		property.setCategory(property.getCategory());
		property.setDescription(property.getDescription());
		property.setImageUrl(property.getImageUrl());
		property.setPrice(property.getPrice());
		property.setStreetAddress(property.getStreetAddress());
		property.setCity(property.getCity());

		return propertyRepo.saveAndFlush(property);
	}

}