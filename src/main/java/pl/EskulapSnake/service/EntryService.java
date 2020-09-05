package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.EmployeeRepository;

@Service
public class EntryService {
    private EmployeeRepository employeeRepository;

    public EntryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
