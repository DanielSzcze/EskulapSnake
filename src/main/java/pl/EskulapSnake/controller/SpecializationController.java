package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.model.Specialization;
import pl.EskulapSnake.service.SpecializationService;

import java.util.List;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    private SpecializationService specializationService;

    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping
    public List<Specialization> getAll(){
        return specializationService.findAll();
    }

    @GetMapping("/{id}")
    public Specialization getSpecializationById(@PathVariable("id") String id){
        return specializationService.findById(Long.parseLong(id));
    }

//    @GetMapping("/employee/{id}")
//    public List<Specialization> getSpecializationByEmployeeId(@PathVariable("id") String id ){
//        return specializationService.findByEmployee(Long.parseLong(id));
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Specialization addSpecialization ( @RequestBody Specialization specialization ) {
        return specializationService.createNew(specialization);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Specialization updateSpecialization ( @RequestBody Specialization specialization, @PathVariable String id ) {
        return specializationService.update(specialization, Long.parseLong(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteSpecializationById (@PathVariable("id") String id){
        specializationService.deleteById(Long.parseLong(id));
    }

}
