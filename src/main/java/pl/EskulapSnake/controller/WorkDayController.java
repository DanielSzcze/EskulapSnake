package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.WorkDayDto;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.service.WorkDayService;

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
    public List<WorkDay> getAllByEmployee(@PathVariable("id_employee") String idEmployee){
        return workDayService.findByEmployee(Long.parseLong(idEmployee));
    }

    @GetMapping("/{id_employee}/{month_and_year}")
    public  List<WorkDay> getAllByEmployeeAndDate(@PathVariable("id_employee") String identification,
                                                  @PathVariable("month_and_year") String monthAndYear){
Long employeeId = Long.parseLong(identification);
return workDayService.findByEmployeeIdAndDate(employeeId, monthAndYear);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkDay addWorkDay (@RequestBody WorkDayDto workDayDto ) {
        return workDayService.createNew(workDayDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkDay updateWorkDay ( @RequestBody WorkDayDto workDayDto, @PathVariable("id") String id ) {
        return workDayService.update(workDayDto, Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteWorkDayById (@PathVariable("id") String id){
        workDayService.deleteByWorkDayId(Long.parseLong(id));
    }

}
