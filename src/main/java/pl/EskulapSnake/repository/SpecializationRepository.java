package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
