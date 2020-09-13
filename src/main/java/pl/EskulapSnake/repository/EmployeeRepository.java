package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.User;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser(User user);

    @Query("SELECT  e FROM Employee e join e.user u WHERE u.username = :username")
    Optional<Employee> findByUserName(@Param("username") String username);
}
