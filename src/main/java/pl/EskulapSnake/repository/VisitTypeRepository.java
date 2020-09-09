package pl.EskulapSnake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.EskulapSnake.model.VisitType;

import java.util.List;

public interface VisitTypeRepository extends JpaRepository<VisitType, Long> {

//    List<VisitType> findByEntry(Long id);
}
