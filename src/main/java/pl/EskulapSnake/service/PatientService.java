package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.PatientRepository;

@Service
public class PatientService {
    private  PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
}
