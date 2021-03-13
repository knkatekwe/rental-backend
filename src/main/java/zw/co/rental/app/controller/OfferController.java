package zw.co.rental.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zw.co.rental.app.entity.Offer;
import zw.co.rental.app.entity.OfferStatus;
import zw.co.rental.app.entity.RentalStatus;
import zw.co.rental.app.entity.User;
import zw.co.rental.app.payload.response.MessageResponse;
import zw.co.rental.app.repository.OfferRepository;
import zw.co.rental.app.repository.PropertyRepository;
import zw.co.rental.app.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/offers")
public class OfferController {
	
	@Autowired
    private OfferRepository offerRepo;
	
	@Autowired
    private PropertyRepository propertyRepo;
	
	@Autowired
    private UserRepository userRepo;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Offer> getAllProperties() {
        return offerRepo.findAll();
    }
	
	@GetMapping(value="/{id}/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Offer> getAllUserOffers(@PathVariable("id") int id) {
        return offerRepo.findAllByUser_Id(id);
    }
	
	@GetMapping(value="/{id}/incoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Offer> getAllOffersForProperties(@PathVariable("id") int id) {
        return offerRepo.findAllByProperty_User_Id(id);
    }
	
	@GetMapping(value="/{id}/property", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Offer> getAllPropertyOffers(@PathVariable("id") int id) {
        return offerRepo.findAllByProperty_Id(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Offer saveOffer(@RequestBody Offer offer, Authentication authentication){
    	
    	User user = userRepo.findById(offer.getUser().getId()).get();
    	
    	offer.setUser(user);
    	offer.setProperty(propertyRepo.findById(offer.getProperty().getId()).get());
    	offer.setStatus(OfferStatus.Pending);
    	offer.setCreatedAt(LocalDateTime.now());
    	
    	Offer _offer = offerRepo.save(offer);
    	
    	propertyRepo.changePropertyStatus(RentalStatus.Requested, offer.getProperty().getId());
    	
        return _offer;
    }
    
    @PostMapping(value="/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelOffer(@PathVariable("id") int id){
    	
    	offerRepo.changeOfferStatus(OfferStatus.Cancelled, id);
    	
        return new ResponseEntity<>(new MessageResponse("Offer cancelled"), HttpStatus.OK);
    }
    
    @PostMapping(value="/{id}/reject", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rejectOffer(@PathVariable("id") int id){
    	
    	offerRepo.changeOfferStatus(OfferStatus.Rejected, id);
    	
        return new ResponseEntity<>(new MessageResponse("Offer rejected"), HttpStatus.OK);
    }
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Offer updateOffer(@RequestBody Offer offer){
    	
    	User user = userRepo.findById(offer.getUser().getId()).get();
    	
    	offer.setUser(user);
    	offer.setProperty(propertyRepo.findById(offer.getProperty().getId()).get());
    	offer.setStatus(offer.getStatus());
    	offer.setAmount(offer.getAmount());
        return offerRepo.saveAndFlush(offer);
    }
    
    @GetMapping(value = "/{offerId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Offer getOffer(@PathVariable("offerId") Integer offerId){
        return offerRepo.findById(offerId).get();

    }

}