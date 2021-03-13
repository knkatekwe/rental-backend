package zw.co.rental.app.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "rentalAgreement"})
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double amount;
	
	@Enumerated(EnumType.STRING)
	private OfferStatus status;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "property_id")
	private Property property;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
	private User user;
	
	private LocalDateTime createdAt;
	
	@OneToOne(mappedBy = "offer", fetch = FetchType.EAGER)
	private RentalAgreement rentalAgreement;

}
