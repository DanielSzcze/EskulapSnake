package pl.EskulapSnake.service;

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
                .orElseThrow(() -> new EntityNotFoundException("Employee with this id not found!"));
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
        Optional<Employee> employeeToUpdate = employeeRepository.findById(id);
        employeeToUpdate.orElseThrow(() -> new EntityNotFoundException("Employee not found!"));
        setFields(employeeToUpdate.get(), employeeDto);
        employeeRepository.save(employeeToUpdate.get());
        return employeeToUpdate.get();
    }

    public Employee findByUser ( User user ) {
        Optional<Employee > employee = employeeRepository.findByUser(user);
        employee.orElseThrow(()-> new EntityNotFoundException("Employee not found!"));
        return employee.get();
    }

    private Employee setFields(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        return  setFields(employee, employeeDto);
    }

    private Employee setFields(Employee employee, EmployeeDto employeeDto) {
        if(!employeeDto.getFirstName().isEmpty() && employeeDto.getFirstName() != null ){
            employee.setFirstName(employeeDto.getFirstName());
        }
        if(!employeeDto.getLastName().isEmpty() && employeeDto.getLastName() != null ){
            employee.setLastName(employeeDto.getLastName());
        }
        if(!employeeDto.getPesel().isEmpty() && employeeDto.getPesel() != null ){
            employee.setPesel(employeeDto.getPesel());
        }
        return employee;
    }

}
