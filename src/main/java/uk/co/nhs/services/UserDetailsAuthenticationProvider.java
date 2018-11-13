package uk.co.nhs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.co.nhs.api.model.User;
import uk.co.nhs.repository.UsersRepository;
import uk.co.nhs.security.jwt.JwtAuthenticationToken;

@Service
public class UserDetailsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsersRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsernameAndPassword(
                            (String) authentication.getPrincipal(),
                            (String) authentication.getCredentials())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + (String) authentication.getPrincipal()));
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}