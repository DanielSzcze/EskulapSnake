package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;

import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByEmployee(Employee employee);
}
