package uk.co.nhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.nhs.api.model.Hospital;

import java.util.List;
import java.util.Optional;


public interface HospitalsRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByHospitalName(String hospitalName);
}