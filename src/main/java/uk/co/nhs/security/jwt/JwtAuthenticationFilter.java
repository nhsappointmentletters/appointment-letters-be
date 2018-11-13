package uk.co.nhs.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Pattern tokenPattern = Pattern.compile("^Bearer\\s([\\w\\-=]+\\.[\\w\\-=]+\\.[\\w\\-=]+)$");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (isNotBlank(authorization)) {
            Matcher matcher = tokenPattern.matcher(authorization);

            if (matcher.matches()) {
                String token = matcher.group(1);

                Authentication authentication = new JwtAuthenticationToken(token);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}
