package zw.co.rental.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zw.co.rental.app.entity.RentalAgreement;
import zw.co.rental.app.entity.RentalAgreementStatus;

@Repository
public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Integer> {
	
	RentalAgreement findByOffer_Id(int id);
		
	RentalAgreement findRentalById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE RentalAgreement r " + "SET r.status = ?1 WHERE r.id = ?2")
	int changeRentalAgreementStatus(RentalAgreementStatus status, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE RentalAgreement r " + "SET r.contractBlockAddress = ?1 WHERE r.id = ?2")
	int changeOfferStatus(String contractBlockAddress, int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE RentalAgreement r " + "SET r.tenantAgreed = ?1 WHERE r.id = ?2")
	int tenantSignature(boolean tenantAgreed, int id);

}
