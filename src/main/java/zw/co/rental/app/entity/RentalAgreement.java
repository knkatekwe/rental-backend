package zw.co.rental.app.entity;

import java.time.LocalDate;
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
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class RentalAgreement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private double amount;

	private int leasePeriod;

	private LocalDate leaseStart;

	private String terms;

	private boolean ownerAgreed;

	private boolean tenantAgreed;

	@Enumerated(EnumType.STRING)
	private RentalAgreementStatus status;
	
	private String tenantWalletAddress;
	
	private String contractBlockAddress;
	
	private LocalDateTime createdAt;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer_id")
	private Offer offer;

}
