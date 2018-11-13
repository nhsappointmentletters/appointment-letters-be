package uk.co.nhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.nhs.api.model.Appointment;
import uk.co.nhs.api.model.Hospital;

import java.util.Optional;


public interface AppoinmentsRepository extends JpaRepository<Appointment, Long> {
}