package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.RegisterRequest;
import pl.EskulapSnake.repository.VerificationCodeRepository;
import pl.EskulapSnake.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthService authService;

    @Autowired
    public AuthorizationController(AuthService authService) {
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
        return new ResponseEntity<>("You have positively verified your account, congrats!", HttpStatus.ACCEPTED);
    }




}
