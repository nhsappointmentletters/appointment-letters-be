package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.api.exception.ResourceNotFoundException;
import uk.co.nhs.api.model.Appointment;
import uk.co.nhs.api.model.Hospital;
import uk.co.nhs.api.model.User;
import uk.co.nhs.api.responses.AppointmentsResponse;
import uk.co.nhs.repository.AppoinmentsRepository;
import uk.co.nhs.repository.HospitalsRepository;
import uk.co.nhs.repository.UsersRepository;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class AppointmentResource {

    public static final int NUMBER_OF_APPOINTMENTS = 2;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private HospitalsRepository hospitalsRepository;
    @Autowired
    private AppoinmentsRepository appoinmentsRepository;

    @DeleteMapping("/user/{id}/appointments")
    public ResponseEntity<?> deleteAppointments(@PathVariable("id") final Long id) {
        return  usersRepository.findById(id)
                .map(user ->
                {
                    deleteAppointments(user);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);

                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }


    @PostMapping("/user/{id}/appointments")
    public ResponseEntity<?> createAppointments(@PathVariable("id") final Long id) {
       return  usersRepository.findById(id)
                .map(user ->
                {
                           createAppointments(user);
                           return new ResponseEntity<>(HttpStatus.CREATED);

                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/user/{id}/appointments")
    public ResponseEntity<?> getAppointments(@PathVariable("id") final Long id) {
        return  usersRepository.findById(id)
                .map(user ->
                {
                    AppointmentsResponse response = new AppointmentsResponse();
                    response.setHospitals(user.getHospitals());
                    return new ResponseEntity<>(response, HttpStatus.CREATED);

                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private void deleteAppointments(User user) {
        Set<Hospital> hospitals = new HashSet<>(user.getHospitals());
        hospitals.stream()
                .filter(hospital -> !hospital.getAppointments().isEmpty())
                .forEach(hospital -> {
                    hospital.getAppointments().clear();
                    hospitalsRepository.save(hospital);
        });
    }

    private void createAppointments(User user) {
        Set<Hospital> userHospitals = new HashSet<>(user.getHospitals());
        for(Hospital hospital : userHospitals) {
            for(int i = 1; i<= NUMBER_OF_APPOINTMENTS; i++){
                Appointment appointment = new Appointment();
                appointment.setDateOfAppointment(createRandomDate());
                appointment.setTimeOfAppointment(createRandomTime());
                appointment.setHospital(hospital);
                appoinmentsRepository.save(appointment);
            }
        }

    }

    public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        return LocalDate.of(2019, month, day);
    }

    public static String createRandomTime() {
        List<String> times = Arrays.asList("08:30", "09:30", "10:30", "11:30am");
        return times.get(new Random().nextInt(times.size()));
    }
}
