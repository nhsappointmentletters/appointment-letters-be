package uk.co.nhs.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class JwtConfig {

    @Value("${jwt.expiration.seconds}")
    private int jwtExpirationSeconds;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtTokenService jwtTokenService(ObjectMapper objectMapper) {
        return new JwtTokenService(
                jwtExpirationSeconds,
                jwtSecret,
                jwtHashingAlgorithm(),
                clock(),
                objectMapper);
    }

    protected SignatureAlgorithm jwtHashingAlgorithm() {
        return SignatureAlgorithm.HS512;
    }

    protected Clock clock() {
        return Clock.systemUTC();
    }

}
