package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.EskulapSnake.service.WorkDayService;

import javax.persistence.Entity;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

    private WorkDayService workDayService;

    @Autowired
    public WorkDayController(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }

}
