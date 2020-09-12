package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.RegisterRequest;
import pl.EskulapSnake.exceptions.AlreadyExistsException;
import pl.EskulapSnake.service.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthService authService;
    private final String tryAgainButton = "<button type=\"button\" onclick=\"location.href='/login'\">Try again</button>";

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
    public ResponseEntity<String> signup(RegisterRequest registerRequest, HttpServletResponse response) {
        try {
            authService.signup(registerRequest);
            response.sendRedirect("/auth/signup/success");
        } catch (AlreadyExistsException e) {
            return new ResponseEntity<>(
                    "<h1>Username or email already exist</h1>" + tryAgainButton,
                    HttpStatus.CONFLICT);
        } catch (IOException e) {
            return new ResponseEntity<>("<h1>There was an error during operation :(</h1>" + tryAgainButton,
                    HttpStatus.BAD_GATEWAY);
        }
        return null;

    }

    @GetMapping("/signup/success")
    public ResponseEntity<String> getSuccess() {
        return new ResponseEntity<>(
                "Registration succeeded, check for your email for message to verify your account",
                HttpStatus.OK);
    }

    @GetMapping("verifyAccount/{code}")
    public ResponseEntity<String> verifyAccount(@PathVariable("code") String code) {
        authService.verifyAccount(code);
        return new ResponseEntity<>("You have positively verified your account, congrats!", HttpStatus.ACCEPTED);
    }


}
