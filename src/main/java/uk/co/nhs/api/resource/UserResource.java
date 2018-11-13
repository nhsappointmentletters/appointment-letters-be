package uk.co.nhs.api.resource;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nhs.api.dto.UserDTO;
import uk.co.nhs.api.dto.UserUpdateRequest;
import uk.co.nhs.api.exception.ResourceNotFoundException;
import uk.co.nhs.api.model.Email;
import uk.co.nhs.api.model.User;
import uk.co.nhs.api.responses.Message;
import uk.co.nhs.repository.UsersRepository;
import uk.co.nhs.services.EmailService;
import uk.co.nhs.utils.RandomPasswordGenerator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
public class UserResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RandomPasswordGenerator randomPasswordGenerator;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") final Long id) {
        return usersRepository.findById(id)
                .map(user -> new ResponseEntity<>(convertToEntity(user), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return userCreationChecks(user);
    }

    private ResponseEntity<?> userCreationChecks(User user) {
        Optional<User> userByUsername = usersRepository.findByUsername(user.getUsername());
        if(userByUsername.isPresent()) {
            return new ResponseEntity<>(new Message("user already exists with the username " + user.getUsername()), HttpStatus.CONFLICT);
        }
        Optional<User> userByEmail = usersRepository.findByEmail(user.getEmail());
        if(userByEmail.isPresent()) {
            return new ResponseEntity<>(new Message( "user already exists with the email " + user.getEmail()),HttpStatus.CONFLICT);
        }
        User save = usersRepository.save(user);
        return new ResponseEntity<>("{\"id\" :\""+user.getId()+"\"}",HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateUser(@PathVariable("id") final Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return  usersRepository.findById(id)
                .map( existingUser -> {
                    existingUser.setEmail(userUpdateRequest.getEmail());
                    existingUser.setFirstName(userUpdateRequest.getFirstName());
                    existingUser.setLastName(userUpdateRequest.getLastName());
                    existingUser.setDateOfBirth(userUpdateRequest.getDateOfBirth());
                    usersRepository.save(existingUser);
                    return new ResponseEntity<>(HttpStatus.OK);
        })
        .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(HttpServletRequest request, @RequestParam("username") final String username) {

        usersRepository.findByUsername(username)
                .map(user -> {
                    String password = randomPasswordGenerator.generate();
                    user.setPassword(password);
                    usersRepository.save(user);

                    Email email = Email.builder()
                            .to(user.getEmail())
                            .subject("Reset your NHS appointment letters password")
                            .body(createEmailBody(user.getUserFullName(), password))
                            .build();

                    emailService.sendEmail(email);
                    return new ResponseEntity<>(HttpStatus.OK);
                });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String createEmailBody(String name, String password) {

        return  "Hello "+ name +",\n\n" +
                    "We have received a request to reset your password.\n\n" +
                    "Your Temporary Password :"+ password+"\n\n" +
                    "If you have not requested a password reset then please contact us immediately. \n\n" +
                    "Our phone number is 0161 875 1414.\n\n" +
                    "Thank you.\n\n" +
                    "NHS Appointment letters";
    }

    private User convertToEntity(UserDTO userDTO) {
        return  modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToEntity(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
