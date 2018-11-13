package uk.co.nhs.api.responses;

import lombok.Data;
import uk.co.nhs.api.model.Hospital;

import java.util.HashSet;
import java.util.Set;

@Data
public class AppointmentsResponse {
    private Set<Hospital> hospitals = new HashSet<>();
}
