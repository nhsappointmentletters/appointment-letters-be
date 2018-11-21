package uk.co.nhs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.co.nhs.api.model.Appointment;
import uk.co.nhs.api.model.Hospital;

import java.util.Optional;


public interface AppoinmentsRepository extends JpaRepository<Appointment, Long> {

//    public static final String FIND_DOCUMENT = "SELECT a.document from Appointment a where a.id = :id";
//
//    @Query(value = FIND_DOCUMENT, nativeQuery = true)
//    public byte[] findDocumentById(@Param("id") Long id);
}
