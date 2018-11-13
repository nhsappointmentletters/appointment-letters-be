package uk.co.nhs.api.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(long id) {
        super("could not find the resource with id: '" + id + "'");
    }
}
