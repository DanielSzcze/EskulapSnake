package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
