package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.EntryDto;
import pl.EskulapSnake.model.*;
import pl.EskulapSnake.repository.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class EntryService {
    private EmployeeService employeeService;
    private EntryRepository entryRepository;
    private PatientRepository patientRepository;
    private VisitTypeRepository visitTypeRepository;
    private UserRepository userRepository;
    private PatientService patientService;

    public EntryService(EntryRepository entryRepository,
                        EmployeeService employeeService,
                        PatientRepository patientRepository,
                        VisitTypeRepository visitTypeRepository,
                        UserRepository userRepository,
                        PatientService patientService) {
        this.entryRepository = entryRepository;
        this.employeeService = employeeService;
        this.patientRepository = patientRepository;
        this.visitTypeRepository = visitTypeRepository;
        this.userRepository = userRepository;
        this.patientService = patientService;

    }

    public List<Entry> findAll() {
        List<Entry> entries = entryRepository.findAll();
        return entries;
    }

    public Entry findById(Long id) {
        return entryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entry found with this id"));
    }

    public List<Entry> findByEmployeeIdAndDate(Long employeeId, String monthAndYear) {
        String[] dateInArray = monthAndYear.split("\\.");
        int month = Integer.parseInt(dateInArray[0]);
        int year = Integer.parseInt(dateInArray[1]);
        if (year < 100) year += 2000;
        LocalDateTime beginOfMonth = this.getBeginOfMonth(month, year);
        LocalDateTime endOfMonth = this.getEndOfMonth(month, year);
        return entryRepository.getByEmpIdAndTimeInterval(employeeId, beginOfMonth, endOfMonth);
    }

    public List<Entry> findByUserNameAndDate(String userName, String monthAndYear) {
        Employee employee = employeeService.findByUserName(userName);
        return this.findByEmployeeIdAndDate(employee.getId(), monthAndYear);
    }

    private LocalDateTime getBeginOfMonth(Integer month, Integer year) {
        LocalDateTime minDateOfMonth = LocalDateTime.of(year, month, 1, 00, 00);
        return minDateOfMonth;

    }

    private LocalDateTime getEndOfMonth(Integer month, Integer year) {

        LocalDateTime maxDateOfMonth = LocalDateTime.of(year, month + 1, 1, 23, 59).minusDays(1);
        return maxDateOfMonth;
    }

    @Transactional
    public void deleteAll() {
        entryRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        Entry entryToDelete = entryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no entry with this id"));
        Patient owner = patientService.findOwnerOfEntry(entryToDelete);

        owner.getEntries().remove(entryToDelete);
        patientRepository.save(owner);
        entryRepository.delete(entryToDelete);
    }

    //TODO  get this method to Patient repository and find by query


    @Transactional
    public Entry createNew(EntryDto entryDto) {
        Patient patient;
        Employee employee;
        if (entryDto.getPatientId() != 0) patient = patientRepository.findById(entryDto.getPatientId()).orElseThrow(()
                -> new EntityNotFoundException("There is no patient with this id"));
        else patient = patientRepository.findByUserName(entryDto.getUserNameOfPatient()).orElseThrow(()
                -> new EntityNotFoundException("There is no patient with this userName"));
        if (entryDto.getEmployeeId() != 0) employee = employeeService.findById(entryDto.getEmployeeId());
        else employee = employeeService.findByUserName(entryDto.getUserNameOfEmployee());
        VisitType visitType = visitTypeRepository.findById(entryDto.getVisitTypeId()).orElseThrow(()
                -> new EntityNotFoundException("There is no visit type with this id"));
        Entry entryToSave = getEntityFromDto(entryDto);
        entryToSave.setVisitType(visitType);
        entryToSave.setEmployee(employee);
        entryRepository.save(entryToSave);
        patient.getEntries().add(entryToSave);
        patientRepository.save(patient);

        return entryToSave;


    }

    @Transactional
    public Entry update(Long id, EntryDto entryDto) {
        Entry entryToUpdate = entryRepository.findById(id).orElseThrow(()

                -> new EntityNotFoundException("There is no entity with this id"));
        entryToUpdate = setFields(entryToUpdate, entryDto);
        entryRepository.save(entryToUpdate);
        return entryToUpdate;

    }

    private Entry setFields(Entry entryToUpdate, EntryDto entryDto) {
        if (entryDto.getExamination() != null) entryToUpdate.setExamination(entryDto.getExamination());
        if (entryDto.getRecommendations() != null) entryToUpdate.setRecommendations(entryDto.getRecommendations());
        entryToUpdate.setLocalDateTime(LocalDateTime.now());
        return entryToUpdate;
    }

    private Entry getEntityFromDto(EntryDto entryDto) {
        Entry entryToUpdate = new Entry();
        if (entryDto.getExamination() != null) entryToUpdate.setExamination(entryDto.getExamination());
        if (entryDto.getRecommendations() != null) entryToUpdate.setRecommendations(entryDto.getRecommendations());
        if (entryDto.getLocalDateTime() != null) entryToUpdate.setLocalDateTime(entryDto.getLocalDateTime());
        return entryToUpdate;
    }


    public Set<Entry> findEntriesByPatientId(long patientId) {
        return (patientRepository.findById(patientId).orElseThrow(()
                -> new EntityNotFoundException("There is no patient with this id"))).getEntries();
    }

    public long[] findEntriesIdByPatientsUserName(String username) {
        Patient patient = patientService.findByUserName(username);
        long[] result = patient.getEntries().stream()
                .map(entry -> entry.getId())
                .mapToLong(e->e)
                .toArray();
        return  result;
    }
}
