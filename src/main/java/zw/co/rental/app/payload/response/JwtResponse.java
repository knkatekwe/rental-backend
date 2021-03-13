package zw.co.rental.app.payload.response;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {
	
	private String token;
	
	private int id;
	private String username;
	private String walletAddress;
	private String phoneNumber;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, int id, String username, String walletAddress, String phoneNumber, String email,
			List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.walletAddress = walletAddress;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.roles = roles;
	}
}
