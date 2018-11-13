package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.api.dto.HospitalDTO;
import uk.co.nhs.api.exception.ResourceNotFoundException;
import uk.co.nhs.api.model.Hospital;
import uk.co.nhs.repository.HospitalsRepository;
import uk.co.nhs.repository.UsersRepository;

@RestController
@Slf4j
public class HospitalResource {

    @Autowired
    private HospitalsRepository hospitalsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user/{id}/hospitals")
    public ResponseEntity<?> getHospitalsByUser(@PathVariable("id") final Long id) {
        return usersRepository.findById(id)
                .map(user -> new ResponseEntity<>(user.getHospitals(), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity<?> getHospital(@PathVariable("id") final Long id) {
        return hospitalsRepository.findById(id)
                .map(hospital -> new ResponseEntity<>(convertToEntity(hospital), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping("/user/{id}/hospital")
    public ResponseEntity<?> createHospital(@PathVariable("id") final Long id,
                                            @RequestBody HospitalDTO hospitalDTO) {
       return  usersRepository.findById(id)
                .map(user -> {
                    Hospital hospital = convertToEntity(hospitalDTO);
                    hospital.setUser(user);
                    hospitalsRepository.save(hospital);
                    return new ResponseEntity<>("{\"id\" :\"" + hospital.getId() + "\"}", HttpStatus.CREATED);
                }).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PutMapping("/user/hospital/{id}")
    public ResponseEntity<?> updateHospital(@PathVariable("id") final Long id, @RequestBody HospitalDTO hospitalDTO) {
        return  hospitalsRepository.findById(id)
                .map( existingHospital -> {
                    existingHospital.setHospitalName(hospitalDTO.getHospitalName());
                    hospitalsRepository.save(existingHospital);
                    return new ResponseEntity<>(HttpStatus.OK);
        })
        .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private HospitalDTO convertToEntity(Hospital hospital) {
        return modelMapper.map(hospital, HospitalDTO.class);
    }

    private Hospital convertToEntity(HospitalDTO hospitalDTO) {
        return  modelMapper.map(hospitalDTO, Hospital.class);
    }
}
