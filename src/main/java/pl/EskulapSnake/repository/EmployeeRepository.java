package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
