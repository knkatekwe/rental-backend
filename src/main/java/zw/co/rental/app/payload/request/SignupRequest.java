package zw.co.rental.app.payload.request;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
	
	private String username;
	private String email;
	private String walletAddress;
	private String phoneNumber;
	private String password;
	private Set<String> role;
	
}
