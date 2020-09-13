package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.service.EmployeeService;
import pl.EskulapSnake.service.EntryService;
import pl.EskulapSnake.service.UserService;
import pl.EskulapSnake.utilities.Utility;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {

    private EntryService entryService;
    private Authentication authentication;
    private UserService userService;
    private EmployeeService employeeService;

    @Autowired
    public EntryController(EntryService entryService,
                           UserService userService,
                           EmployeeService employeeService) {
        this.entryService = entryService;
        this.userService = userService;
        this.entryService = entryService;
    }


    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Entry> getAll() {
        return entryService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Entry findByid(@PathVariable("id") String identification) {
        long id = Long.parseLong(identification);
        return entryService.findById(id);
    }

    @GetMapping("/{id_employee}/{month_and_year}")
    public List<Entry> getAllByEmployeeAndDate(@PathVariable("id_employee") String identification,
                                               @PathVariable("month_and_year") String monthAndYear) {
        Long employeeId = Long.parseLong(identification);
        return entryService.findByEmployeeIdAndDate(employeeId, monthAndYear);
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Entry post(@RequestBody EntryDto entryDto) {
        return entryService.createNew(entryDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Entry put(@PathVariable("id") String identification, @RequestBody EntryDto entryDto) {
        long id = Long.parseLong(identification);
        return entryService.update(id, entryDto);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.GONE)
    public void deleteAll() {
        entryService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable("id") String identification) {


        long id = Long.parseLong(identification);
        entryService.deleteById(id);
    }

}
