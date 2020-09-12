package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.WorkDay;

import java.time.LocalDateTime;
import java.util.List;

public interface EntryRepository  extends JpaRepository<Entry, Long> {


    @Query("SELECT en FROM  Entry en JOIN  en.employee em WHERE  em.id = :employee_id AND en.localDateTime BETWEEN :from_date and  :to_date")
    public List<Entry> getByEmpIdAndTimeInterval(@Param("employee_id") Long EmployeeId,
                                                   @Param("from_date") LocalDateTime BeginMonth,
                                                   @Param("to_date") LocalDateTime EndMonth);
}
