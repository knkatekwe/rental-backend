package zw.co.rental.app.service;

import java.util.List;
import zw.co.rental.app.entity.Property;

public interface RentalService {
	
	public List<Property> getRentals();
	
    public Property getRental(Integer rentalId);
    
    public boolean saveRental(Property rental);
    
}
