package uk.co.nhs.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class JwtTokenService {

    private final int tokenExpirationInSeconds;
    private final String secret;
    private final SignatureAlgorithm hashingAlgorithm;
    private final Clock clock;
    private ObjectMapper objectMapper;

    public JwtTokenService(int tokenExpirationInSeconds, String secret, SignatureAlgorithm hashingAlgorithm, Clock clock, ObjectMapper objectMapper) {
        this.tokenExpirationInSeconds = tokenExpirationInSeconds;
        this.secret = secret;
        this.hashingAlgorithm = hashingAlgorithm;
        this.clock = clock;
        this.objectMapper = objectMapper;
    }

    public String createToken(Authentication authentication) {

        return Jwts.builder()
                .setClaims(getClaims(authentication))
                .setExpiration(getTokenExpiration())
                .signWith(hashingAlgorithm, secret)
                .compact();
    }

    public String verifyToken(Authentication authentication) {
        String subject;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(authentication.getCredentials().toString()).getBody();

            subject = claims.getSubject();

        } catch (JwtException ex) {
            throw new BadCredentialsException("Invalid Token", ex);
        }

        return subject;
    }

    private Date getTokenExpiration() {
        return Date.from(LocalDateTime.now(clock).plusSeconds(tokenExpirationInSeconds).toInstant(ZoneOffset.UTC));
    }

    private Claims getClaims(Authentication authentication) {
        final Claims claims = Jwts.claims();
        claims.setSubject((String) authentication.getPrincipal());
        return claims;
    }

}
