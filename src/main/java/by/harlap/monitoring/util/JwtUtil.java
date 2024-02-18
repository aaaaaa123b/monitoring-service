package by.harlap.monitoring.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET;
    private static final long TOKEN_VALIDITY_IN_MILLIS;

    private static final Algorithm algorithm;
    private static final JWTVerifier verifier;

    static {
        final PropertyHolder properties = new PropertyHolder("liquibase.properties");

        SECRET = properties.get("secretKey");
        algorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(algorithm).build();

        TOKEN_VALIDITY_IN_MILLIS = Long.parseLong(properties.get("tokenTime"));
    }

    /**
     * Creates a JWT token with the specified username and password.
     *
     * @param username the username to be included in the token
     * @param password the password to be included in the token
     * @return a JWT token
     */
    public static String createJWT(String username, String password) {
        return JWT.create()
                .withClaim("username", username)
                .withClaim("password", password)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
                .sign(algorithm);
    }

    /**
     * Verifies the validity of a JWT token.
     *
     * @param jwtToken the JWT token to be verified
     * @return the decoded JWT if verification is successful, null otherwise
     */
    public static DecodedJWT verifyJWT(String jwtToken) {
        try {
            return verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            System.out.println("JWT verification failed: " + e.getMessage());
        }
        return null;
    }
}