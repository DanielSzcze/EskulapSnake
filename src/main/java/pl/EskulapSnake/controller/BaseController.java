package pl.EskulapSnake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/")
public class BaseController {


    @GetMapping("/recorder")
    public  String getRecorder() {
        return "medicalRecorder";
    }

}