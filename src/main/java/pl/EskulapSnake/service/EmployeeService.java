package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
}
