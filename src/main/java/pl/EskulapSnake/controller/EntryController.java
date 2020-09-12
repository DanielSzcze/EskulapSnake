package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.service.EmployeeService;
import pl.EskulapSnake.service.EntryService;
import pl.EskulapSnake.service.PatientService;
import pl.EskulapSnake.service.UserService;
import pl.EskulapSnake.utilities.Utility;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/entries")
public class EntryController {

    private EntryService entryService;
    private Authentication authentication;
    private UserService userService;
    private EmployeeService employeeService;
    private PatientService patientService;

    @Autowired
    public EntryController(EntryService entryService,
                           UserService userService,
                           EmployeeService employeeService,
                           PatientService patientService) {
        this.entryService = entryService;
        this.userService = userService;
        this.entryService = entryService;
        this.patientService = patientService;
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

    @GetMapping("/patient/{patient_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<Entry> findPatientEntries(@PathVariable("patient_id") String identification){
        return patientService.findById(Long.parseLong(identification)).getEntries();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Entry post( @RequestBody EntryDto entryDto, Authentication authentication) {
        Employee loggedEmployee = Utility.findloggedEmployee(authentication, userService, employeeService);
        return entryService.createNew(entryDto, loggedEmployee);
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
