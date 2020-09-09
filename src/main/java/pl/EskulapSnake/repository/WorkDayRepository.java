package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;

import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    List<WorkDay> findByEmployee(Employee employee);
    @Query("SELECT w FROM  WorkDay  w join Employee  e WHERE e.id= :user_id AND w.from_date BETWEEN :begin_date AND :end_date")
    List<WorkDay> findByUserAndMonth(@Param("user_id") Long EmployeeId,
                                     @Param("begin_date") String BeginMonth,
                                     @Param("end_date") String EndMonth);
}

