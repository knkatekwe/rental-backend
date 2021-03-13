package zw.co.rental.app.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}