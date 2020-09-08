package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.model.Role;
import pl.EskulapSnake.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAll(){
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable("id") String id){
        return roleService.findById(Long.parseLong(id));
    }

//    @GetMapping("/employee/{id}")
//    public List<Role> getRoleByEmployeeId(@PathVariable("id") String id ){
//        return roleService.findByEmployee(Long.parseLong(id));
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole ( @RequestBody Role role ) {
        return roleService.createNew(role);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Role updateRole ( @RequestBody Role role, @PathVariable String id ) {
        return roleService.update(role, Long.parseLong(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteRoleById (@PathVariable("id") String id){
        roleService.deleteById(Long.parseLong(id));
    }

}
