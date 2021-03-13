package zw.co.rental.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zw.co.rental.app.entity.Offer;
import zw.co.rental.app.entity.OfferStatus;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
	
	List<Offer> findAllByUser_Id(int id);
	
	//offers to a user's property
	List<Offer> findAllByProperty_User_Id(int id);
	
	List<Offer> findAllByProperty_Id(int id);
		
	Offer findRentalById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Offer o " + "SET o.status = ?1 WHERE o.id = ?2")
	int changeOfferStatus(OfferStatus status, int id);

}
