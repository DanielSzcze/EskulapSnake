package pl.EskulapSnake.service;

import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EmployeeDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.repository.EmployeeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("In DB no entry with this id"));
    }

    @Transactional
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
    @Transactional
    public Employee createNew(EmployeeDto employeeDto) {
        Employee employeeToSave = setFields(employeeDto);
        employeeRepository.save(employeeToSave);
        return employeeToSave;
    }
    @Transactional
    public Employee update(EmployeeDto employeeDto, long id) {
        Employee employeeToSave = setFields(employeeDto);
        employeeToSave.setId(id);
        employeeRepository.save(employeeToSave);
        return employeeToSave;
    }

    public Employee findByUser ( User user ) {
        Optional<Employee > employee = employeeRepository.findByUser(user);
        employee.orElseThrow(()-> new EntityNotFoundException("Employee not found"));
        return employee.get();
    }

    private Employee setFields(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPesel(employeeDto.getPesel());
        return employee;
    }

}
