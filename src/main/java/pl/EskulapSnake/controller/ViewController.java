package pl.EskulapSnake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/recorder")
    public String getRecorder(Principal principal) {
        return "medicalRecorder";
    }


}
