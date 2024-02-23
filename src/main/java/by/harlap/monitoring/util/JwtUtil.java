package by.harlap.monitoring.util;

import by.harlap.monitoring.config.property.SecurityProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 */
@Component
public class JwtUtil {

    private final SecurityProperties securityProperties;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    /**
     * Constructs a new JwtUtil instance with the specified SecurityProperties.
     *
     * @param securityProperties the SecurityProperties containing JWT-related configuration
     */
    public JwtUtil(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;

        algorithm = Algorithm.HMAC256(securityProperties.getSecret());
        verifier = JWT.require(algorithm).build();
    }

    /**
     * Creates a JWT token with the specified username and password.
     *
     * @param username the username to be included in the token
     * @param password the password to be included in the token
     * @return a JWT token
     */
    public String createJWT(String username, String password) {
        return JWT.create()
                .withClaim("username", username)
                .withClaim("password", password)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getTtl()))
                .sign(algorithm);
    }

    /**
     * Verifies the validity of a JWT token.
     *
     * @param jwtToken the JWT token to be verified
     * @return the decoded JWT if verification is successful, null otherwise
     */
    public DecodedJWT verifyJWT(String jwtToken) {
        try {
            return verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            System.out.println("JWT verification failed: " + e.getMessage());
        }
        return null;
    }
}