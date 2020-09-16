package pl.EskulapSnake.repository;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.EskulapSnake.model.Entry;
import pl.EskulapSnake.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Query("SELECT  p FROM Patient p join p.user u WHERE u.username = :username")
    Optional<Patient> findByUserName(@Param("username") String username);
}
