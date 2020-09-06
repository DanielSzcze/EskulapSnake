package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.service.EmployeeService;
import pl.EskulapSnake.service.EntryService;
import pl.EskulapSnake.service.UserServise;
import pl.EskulapSnake.utilities.Utility;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntryController {

    private EntryService entryService;
    private Authentication authentication;
    private UserServise userServise;
    private EmployeeService employeeService;

    @Autowired
    public EntryController(EntryService entryService,
                           UserServise userServise,
                           EmployeeService employeeService) {
        this.entryService = entryService;
        this.userServise = userServise;
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

    @PostMapping("/{id_patient}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Entry post(@PathVariable("id_patient") String identification,
                      @RequestBody EntryDto entryDto, Authentication authentication) {
        long patientId = Long.parseLong(identification);
        Employee loggedEmployee = Utility.findloggedEmployee(authentication, userServise, employeeService);
        return entryService.createNew(entryDto, patientId, loggedEmployee);
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
    @ResponseStatus(value = HttpStatus.GONE)
    public void deleteById(@PathVariable("id") String identification) {


        long id = Long.parseLong(identification);
        entryService.deleteById(id);
    }

}
