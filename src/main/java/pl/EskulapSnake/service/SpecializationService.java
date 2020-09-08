package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.model.Specialization;
import pl.EskulapSnake.repository.SpecializationRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SpecializationService {
    private SpecializationRepository specializationRepository;

    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public List<Specialization> findAll(){
        return specializationRepository.findAll();
    }

    public Specialization findById(Long id){
        return specializationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Specialization not found!"));
    }

//    public List<Specialization> findByEmployee(Long id) {
//        return specializationRepository.findByEmployee(id);
//    }

    @Transactional
    public void deleteAll () {
        specializationRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        specializationRepository.deleteById(id);
    }

    @Transactional
    public Specialization createNew(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Transactional
    public Specialization update (Specialization specialization, Long id) {
        specialization.setId(id);
        return specializationRepository.save(specialization);
    }

}
