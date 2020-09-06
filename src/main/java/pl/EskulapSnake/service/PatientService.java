package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.PatientDto;
import pl.EskulapSnake.model.Patient;
import pl.EskulapSnake.repository.PatientRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("In db is no Patient with this id"));
    }

    @Transactional
    public void deleteAll() {
        patientRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public Patient createNew(PatientDto patientDto) {
        Patient patientToSave = getEntityFromDto(patientDto);
        patientRepository.save(patientToSave);
        return patientToSave;
    }

    @Transactional
    public Patient update(Long id, PatientDto patientDto) {
        Patient patientToUpdate = this.findById(id);

        patientToUpdate=setFields(patientDto, patientToUpdate);
        return patientToUpdate;
    }

    private Patient setFields(PatientDto patientDto, Patient patientToUpdate) {
        if(patientDto.getFirstName()!=null)patientToUpdate.setFirstName(patientDto.getFirstName());
        if(patientDto.getLastName()!=null)patientToUpdate.setLastName(patientDto.getLastName());
        if(patientDto.getPesel()!=null)patientToUpdate.setPesel(patientDto.getPesel());
        return  patientToUpdate;
    }


    private Patient getEntityFromDto(PatientDto patientDto) {
        Patient result = new Patient();
        result.setFirstName(patientDto.getFirstName());
        result.setLastName(patientDto.getLastName());
        result.setPesel(patientDto.getPesel());
        return result;
    }
}
