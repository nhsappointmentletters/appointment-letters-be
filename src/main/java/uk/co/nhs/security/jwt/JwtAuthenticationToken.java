package uk.co.nhs.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String jwtToken;
    private String principal;

    private JwtAuthenticationToken(){
        super(null);
    }

    public JwtAuthenticationToken(String token){
        super(null);
        this.jwtToken = token;
        this.setAuthenticated(false);
    }

    public static JwtAuthenticationToken validatedFor(String principal) {
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken();
        authenticationToken.principal = principal;
        authenticationToken.setAuthenticated(true);

        return authenticationToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
