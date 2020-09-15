package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.EskulapSnake.dto.EmployeeDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin("*")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") String id) {
        long identification = Long.parseLong(id);
        return employeeService.findById(identification);
    }

    @GetMapping("/user/{userName}")
    public Employee getEmployeeByUserName(@PathVariable("userName") String userName) {
        return employeeService.findByUserName(userName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.createNew(employeeDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee updateEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable String id) {
        return employeeService.update(employeeDto, Long.parseLong(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public void deleteEmployeeById(@PathVariable("id") String id) {
        employeeService.deleteById(Long.parseLong(id));
    }

}
