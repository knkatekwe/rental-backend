package zw.co.rental.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zw.co.rental.app.entity.Property;
import zw.co.rental.app.entity.RentalStatus;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
	
	List<Property> findAllByUser_Id(int id);
		
	Property findRentalById(int id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Property p " + "SET p.status = ?1 WHERE p.id = ?2")
	int changePropertyStatus(RentalStatus status, int id);

}
