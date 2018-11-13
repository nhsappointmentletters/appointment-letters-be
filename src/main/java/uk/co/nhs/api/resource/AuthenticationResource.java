package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.nhs.api.dto.LoginRequest;
import uk.co.nhs.api.responses.AuthenticationTokenResponse;
import uk.co.nhs.repository.UsersRepository;
import uk.co.nhs.security.jwt.JwtTokenService;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationResource {

    public class InvalidPrivilegesException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenGenerator;
    private final int timeToLive;
    private UsersRepository usersRepository;

    public AuthenticationResource(
            @Value("${jwt.expiration.seconds}") int jwtExpirationSeconds,
            AuthenticationManager authenticationManager,
            JwtTokenService tokenGenerator,
            UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.timeToLive = jwtExpirationSeconds;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/generateToken")
    public AuthenticationTokenResponse authenticate(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String token = tokenGenerator.createToken(authentication);
        AuthenticationTokenResponse authenticationResponse = new AuthenticationTokenResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setTimeToLive(timeToLive);
        usersRepository.findByUsername(loginRequest.getUsername())
                .ifPresent(user -> authenticationResponse.setId(user.getId()));
        return authenticationResponse;
    }

}
