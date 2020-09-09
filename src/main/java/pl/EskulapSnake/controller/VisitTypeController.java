package pl.EskulapSnake.controller;

import org.hibernate.hql.internal.ast.util.NodeTraverser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.model.VisitType;
import pl.EskulapSnake.service.VisitTypeService;

import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitTypeController {

    private VisitTypeService visitTypeService;

    @Autowired
    public VisitTypeController(VisitTypeService visitTypeService) {
        this.visitTypeService = visitTypeService;
    }

    @GetMapping
    public List<VisitType> getAll(){
        return visitTypeService.findAll();
    }

    @GetMapping("/{id}")
    public VisitType getRoleById(@PathVariable("id") String id){
        return visitTypeService.findById(Long.parseLong(id));
    }

//    @GetMapping("/entry/{id}")
//    public List<VisitType> getVisitTypeByEntry(@PathVariable("id") String id ){
//        return visitTypeService.findByEntry(Long.parseLong(id));
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitType addVisitType ( @RequestBody VisitType visitType ) {
        return visitTypeService.createNew(visitType);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VisitType updateRole ( @RequestBody VisitType visitType, @PathVariable String id ) {
        return visitTypeService.update(visitType, Long.parseLong(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteRoleById (@PathVariable("id") String id){
        visitTypeService.deleteById(Long.parseLong(id));
    }
}
