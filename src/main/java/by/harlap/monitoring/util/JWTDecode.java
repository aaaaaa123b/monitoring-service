package by.harlap.monitoring.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class JWTDecode {

    private static final String SECRET;
    private static final long TOKEN_VALIDITY_IN_MILLIS;

    static {
        Properties properties = loadProperties();
        SECRET = properties.getProperty("secretKey");
        TOKEN_VALIDITY_IN_MILLIS = Long.parseLong(properties.getProperty("tokenTime"));
    }

    private static Algorithm algorithm;
    private static JWTVerifier verifier;

    public static void initialize() {
        algorithm = Algorithm.HMAC256(SECRET);

        verifier = JWT.require(algorithm)
                .build();
    }

    public static String createJWT(String username, String password) {

        return JWT.create()
                .withClaim("username", username)
                .withClaim("password", password)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLIS))
                .sign(algorithm);
    }

    public static DecodedJWT verifyJWT(String jwtToken) {
        try {
            return verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            System.out.println("JWT verification failed: " + e.getMessage());
        }
        return null;
    }


    private static DecodedJWT decodedJWT(String jwtToken) {
        try {
            return JWT.decode(jwtToken);
        } catch (JWTDecodeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static boolean isJWTExpired(DecodedJWT decodedJWT) {
        Date expiresAt = decodedJWT.getExpiresAt();
        return expiresAt.before(new Date());
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream source = JWTDecode.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
            properties.load(source);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while loading properties", e);
        }
        return properties;
    }
}