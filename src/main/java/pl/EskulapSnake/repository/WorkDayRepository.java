package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByEmployee(Employee employee);
    @Query("SELECT w FROM  WorkDay w JOIN w.employee e WHERE  e.id = :employee_id AND w.fromWorkTime BETWEEN :from_date and  :to_date")
   public List<WorkDay> getByEmpIdAndTimeInterval(@Param("employee_id") Long EmployeeId,
                                                  @Param("from_date") LocalDateTime BeginMonth,
                                                  @Param("to_date") LocalDateTime EndMonth);
}

