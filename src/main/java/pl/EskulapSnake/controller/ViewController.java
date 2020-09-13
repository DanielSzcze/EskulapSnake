package pl.EskulapSnake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/recorder")
    public String getRecorder() {
        return "medicalRecorder";
    }

@GetMapping("/patientsView")
    public String getPatients(){
        return "patientsView";
}

}
