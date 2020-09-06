package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.RegisterRequest;
import pl.EskulapSnake.repository.VerificationCodeRepository;
import pl.EskulapSnake.service.AuthService;
import pl.EskulapSnake.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthService authService;

    @Autowired
    public AuthorizationController(AuthService authService, VerificationCodeRepository codeRepository) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User signed up", HttpStatus.OK);
    }

    @GetMapping("verifyAccount/{code}")
    public ResponseEntity<String> verifyAccount(@PathVariable("code") String code) {
        authService.verifyAccount(code);
        return new ResponseEntity<>(code, HttpStatus.ACCEPTED);
    }

    @Value("${connection.address}")
    public static String connAddr;

    @Value("${connection.port}")
    public static String port;

}
