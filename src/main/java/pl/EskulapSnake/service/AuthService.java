package pl.EskulapSnake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.RegisterRequest;
import pl.EskulapSnake.exceptions.AlreadyExistsException;
import pl.EskulapSnake.model.NotificationEmail;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.model.VerificationCode;
import pl.EskulapSnake.repository.UserRepository;
import pl.EskulapSnake.repository.VerificationCodeRepository;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final MailService mailService;
    private final ApplicationContext applicationContext;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationCodeRepository verificationCodeRepository, MailService mailService, ApplicationContext applicationContext) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationCodeRepository = verificationCodeRepository;
        this.mailService = mailService;
        this.applicationContext = applicationContext;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) throws AlreadyExistsException {
        User user = userFromRegisterRequest(registerRequest);
        if (emailExists(registerRequest.getEmail())) {
            throw new AlreadyExistsException("There is account with that email address");
        }
        if (userExists(registerRequest.getUsername())) {
            throw new AlreadyExistsException("There is already signed user with this name");
        }
        userRepository.save(user);

        VerificationCode code = generateVerificationCode(user);


        mailService.sendMessage(new NotificationEmail(
                "Confirmation of registration on EskulapSnake App",
                user.getEmail(),
                "Hey " + user.getUsername() + ", thanks for joining our app! \n" +
                        "Please click the link below to confirm your e-mail: \b" +
                        "http://" + applicationContext.getEnvironment().getProperty("connection.address") +
                        ":" + applicationContext.getEnvironment().getProperty("connection.port") +
                        "/auth/verifyAccount/" + code.getCode()
        ));
    }

    private boolean userExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    @Transactional
    VerificationCode generateVerificationCode(User user) {
        String code = UUID.randomUUID().toString();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setUser(user);
        verificationCodeRepository.save(verificationCode);
        return verificationCode;
    }

    @Transactional
    public void verifyAccount(String code) {
        Optional<VerificationCode> verificationCode = verificationCodeRepository.findByCode(code);
        verificationCode.orElseThrow(() -> new EntityNotFoundException("Wrong code passed"));
        User user = verificationCode.get().getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    private User userFromRegisterRequest(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnabled(false);
        user.setRoles(Collections.singletonList(new Role("ROLE_PATIENT")));
        return user;
    }
}
