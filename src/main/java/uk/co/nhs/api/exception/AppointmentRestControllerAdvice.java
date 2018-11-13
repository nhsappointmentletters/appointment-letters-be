package uk.co.nhs.api.exception;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uk.co.nhs.api.resource.AuthenticationResource;

@RestControllerAdvice
public class AppointmentRestControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors bookNotFoundExceptionHandler(ResourceNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ExceptionHandler(AuthenticationResource.InvalidPrivilegesException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public VndErrors invalidPrivileges(AuthenticationResource.InvalidPrivilegesException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public VndErrors badCredentials(BadCredentialsException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}