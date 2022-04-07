package davidgoldstein.blackjack.service;

import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthService {

    final static String adminToken = "changeme";

    public void validateAdminToken(String token) throws AuthenticationException {
        if (token != adminToken) {
            throw new AuthenticationException("invalid token");
        }
    }
}
