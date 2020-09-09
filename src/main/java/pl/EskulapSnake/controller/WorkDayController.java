package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.service.WorkDayService;

import javax.persistence.Entity;
import java.util.List;

@RestController
@RequestMapping("/workdays")
public class WorkDayController {

    private WorkDayService workDayService;

    @Autowired
    public WorkDayController(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }

    @GetMapping
    public List<WorkDay> getAll(){
        return workDayService.findAll();
    }

    @GetMapping("/{id_employee}")
    public List<WorkDay> getAllByEmployee(@RequestBody String idEmployee){
        return workDayService.findByEmployee(Long.parseLong(idEmployee));
    }

    @GetMapping("/{id_employee}/{month_and_year}")
    public  List<WorkDay> getAllByEmployeeAndMonth(@PathVariable("id_employee") String identification,
                                                   @PathVariable("month_and_year") String monthAndYear){
Long employeeId = Long.parseLong(identification);
return workDayService.findByEmployeeAndMonth(employeeId, monthAndYear);
    }

}
