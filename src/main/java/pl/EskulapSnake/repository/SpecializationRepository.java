package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Specialization;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

//    List<Specialization> findByEmployee(Long id);


}
