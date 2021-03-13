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

import zw.co.rental.app.entity.OfferStatus;
import zw.co.rental.app.entity.RentalAgreement;
import zw.co.rental.app.entity.RentalAgreementStatus;
import zw.co.rental.app.payload.response.MessageResponse;
import zw.co.rental.app.repository.RentalAgreementRepository;
import zw.co.rental.app.repository.OfferRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/rental-agreements")
public class RentalAgreementController {

	@Autowired
	private RentalAgreementRepository rentalAgreementRepo;

	@Autowired
	private OfferRepository offerRepo;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentalAgreement> getAllProperties() {
		return rentalAgreementRepo.findAll();
	}

	@GetMapping(value = "{id}/offer", produces = MediaType.APPLICATION_JSON_VALUE)
	public RentalAgreement getAllOfferRentalAgreements(@PathVariable("id") int id) {
		return rentalAgreementRepo.findByOffer_Id(id);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RentalAgreement saveRentalAgreement(@RequestBody RentalAgreement rentalAgreement,
			Authentication authentication) {

		rentalAgreement.setOffer(offerRepo.findById(rentalAgreement.getOffer().getId()).get());
		rentalAgreement.setStatus(RentalAgreementStatus.Pending);
		rentalAgreement.setCreatedAt(LocalDateTime.now());
		rentalAgreement.setOwnerAgreed(true);

		RentalAgreement _rentalAgreement = rentalAgreementRepo.save(rentalAgreement);

		offerRepo.changeOfferStatus(OfferStatus.Accepted, rentalAgreement.getOffer().getId());

		return _rentalAgreement;
	}
	
	@PostMapping(value="{rentalAgreementId}/accept", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> acceptRentalAgreement(@PathVariable("rentalAgreementId") int rentalAgreementId) {

		rentalAgreementRepo.changeRentalAgreementStatus(RentalAgreementStatus.Accepted, rentalAgreementId);
		
		rentalAgreementRepo.tenantSignature(true, rentalAgreementId);

		return new ResponseEntity<>(new MessageResponse("Rental agreement accepted & signed"), HttpStatus.OK);
	}
	
	@PostMapping(value="{rentalAgreementId}/rejected", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rejectRentalAgreement(@PathVariable("rentalAgreementId") int rentalAgreementId) {

		rentalAgreementRepo.changeRentalAgreementStatus(RentalAgreementStatus.Rejected, rentalAgreementId);

		return new ResponseEntity<>(new MessageResponse("Rental agreement accepted"), HttpStatus.OK);
	}

	@PostMapping(value="{rentalAgreementId}/{offerId}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> cancelRentalAgreement(@PathVariable("rentalAgreementId") int rentalAgreementId,
			@PathVariable("offerId") int offerId) {

		rentalAgreementRepo.changeRentalAgreementStatus(RentalAgreementStatus.Terminated, rentalAgreementId);

		offerRepo.changeOfferStatus(OfferStatus.Cancelled, offerId);

		return new ResponseEntity<>(new MessageResponse("Rental agreement cancelled"), HttpStatus.OK);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public RentalAgreement updateRentalAgreement(@RequestBody RentalAgreement rentalAgreement) {

		rentalAgreement.setOffer(offerRepo.findById(rentalAgreement.getOffer().getId()).get());
		rentalAgreement.setStatus(rentalAgreement.getStatus());
		rentalAgreement.setTenantAgreed(rentalAgreement.isTenantAgreed());
		rentalAgreement.setAmount(rentalAgreement.getAmount());
		rentalAgreement.setContractBlockAddress(rentalAgreement.getContractBlockAddress());
		rentalAgreement.setTenantWalletAddress(rentalAgreement.getTenantWalletAddress());
		return rentalAgreementRepo.saveAndFlush(rentalAgreement);
	}

	@GetMapping(value = "{rentalAgreementId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public RentalAgreement getRentalAgreement(@PathVariable("rentalAgreementId") Integer rentalAgreementId) {
		return rentalAgreementRepo.findById(rentalAgreementId).get();

	}

}