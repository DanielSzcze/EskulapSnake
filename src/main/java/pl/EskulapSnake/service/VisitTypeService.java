package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.VisitTypeRepository;

@Service
public class VisitTypeService {
    private VisitTypeRepository visitTypeRepository;

    public VisitTypeService (VisitTypeRepository visitTypeRepository) {
        this.visitTypeRepository = visitTypeRepository;
    }
}
