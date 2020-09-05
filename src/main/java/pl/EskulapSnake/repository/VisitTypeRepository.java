package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.VisitType;

public interface VisitTypeRepository extends JpaRepository<VisitType, Long> {
}
