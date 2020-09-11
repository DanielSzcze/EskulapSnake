package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.RegisterRequest;
import pl.EskulapSnake.service.AuthService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthService authService;

    @Autowired
    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signup")
    public String getUserForm(Model model) {
        RegisterRequest registerRequest = new RegisterRequest();
        model.addAttribute("signup", registerRequest);
        return "signup";
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void signup(RegisterRequest registerRequest, HttpServletResponse response) throws Exception {
        authService.signup(registerRequest);
        response.sendRedirect("/auth/signup/success");
    }

    @GetMapping("/signup/success")
    public ResponseEntity<String> getSuccess() {
        return new ResponseEntity<>(
                "Registration succeded, check for your email for message to verify your account",
                HttpStatus.OK);
    }

    @GetMapping("verifyAccount/{code}")
    public ResponseEntity<String> verifyAccount(@PathVariable("code") String code) {
        authService.verifyAccount(code);
        return new ResponseEntity<>("You have positively verified your account, congrats!", HttpStatus.ACCEPTED);
    }

    @GetMapping("/success")
    public ResponseEntity<String> logoutSuccessful() {
        return new ResponseEntity<>("Logout successful", HttpStatus.ACCEPTED);
    }

}
