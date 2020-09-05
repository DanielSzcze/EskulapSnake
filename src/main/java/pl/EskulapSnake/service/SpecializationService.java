package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.SpecializationRepository;

@Service
public class SpecializationService {
    private SpecializationRepository specializationRepository;
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }
}
