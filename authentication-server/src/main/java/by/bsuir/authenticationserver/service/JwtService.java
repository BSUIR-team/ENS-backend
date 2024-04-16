package by.bsuir.authenticationserver.service;

import by.bsuir.authenticationserver.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

//    public static final String ALGORITHM = "HmacSHA256";
    private final MacAlgorithm algorithm = Jwts.SIG.HS256;
    private final Key key = algorithm.key().build();
    private SecretKeySpec secretKey;

    @Value("${auth.secret}")
    private String secret;

    public Boolean validateToken(final String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSignKey())
                .build();
        try {
            parser.parseSignedClaims(token);
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
        return true;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claims(new HashMap<>())
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey())
                .compact();
    }

    private Boolean isExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    public String extractEmail(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String extractJwt(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
