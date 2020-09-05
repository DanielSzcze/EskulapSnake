package pl.EskulapSnake.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.EskulapSnake.model.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
}
