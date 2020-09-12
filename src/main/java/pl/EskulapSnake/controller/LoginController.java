package pl.EskulapSnake.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }


    @GetMapping("/login/error")
    @ResponseBody
    ResponseEntity<String> loginFailure(@RequestParam(name = "error") boolean error){
        if(error){
            return new ResponseEntity<>("<h3>Failed to log in. Check if your password or username is correct</h3> " +
                    "<button type=\"button\" onclick=\"location.href='/login'\">Try again</button>"
                    , HttpStatus.CONFLICT);
        } return null;
    }
}
