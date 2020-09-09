package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;

import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByEmployee(Employee employee);
    @Query("SELECT w FROM  WorkDay  w join Employee  e WHERE e.id= :employee AND w.fromWorkTime BETWEEN :from_date AND :end_date")
   public List<WorkDay> getAllByEmployeeIdAndTimeInterval(@Param("employee") Long EmployeeId,
                                                          @Param("from_date") String BeginMonth,
                                                          @Param("end_date") String EndMonth);
}

