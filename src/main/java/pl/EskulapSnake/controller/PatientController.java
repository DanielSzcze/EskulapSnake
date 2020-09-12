package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.PatientDto;
import pl.EskulapSnake.model.Patient;
import pl.EskulapSnake.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<Patient> findAll() {
        return patientService.findAll();

    }

    @GetMapping("/{id:\\d+}")
    @ResponseStatus(value = HttpStatus.OK)
    public Patient findById(@PathVariable("id") String identification) {
        long id = Long.parseLong(identification);
        return patientService.findById(id);
    }
    @GetMapping("/{partOfDate:[a-z]+}")
    @ResponseStatus(value = HttpStatus.OK)
    public  List<Patient> getPapientsByPartOfDate(@PathVariable("partOfDate")String partOfdate) {
        return patientService.findByPartOfDate(partOfdate);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Patient post(@RequestBody PatientDto patientDto) {
        return patientService.createNew(patientDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Patient put(@PathVariable("id") String identification, @RequestBody PatientDto patientDto) {
        long id = Long.parseLong(identification);
        return patientService.update(id, patientDto);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.GONE)
    public void deleteAll() {
        patientService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.GONE)
    public void deleteById(@PathVariable("id") String identification) {
        long id = Long.parseLong(identification);
        patientService.deleteById(id);
    }
}
