package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
