package zw.co.rental.app.payload.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.rental.app.entity.OfferStatus;

@Data
@NoArgsConstructor
public class OfferUpdate {
	
	private double amount;
	private OfferStatus status;

}
