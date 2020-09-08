package pl.EskulapSnake.service;

import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.WorkDayDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.repository.EmployeeRepository;
import pl.EskulapSnake.repository.WorkDayRepository;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class WorkDayService {

    private WorkDayRepository workDayRepository;
    private EmployeeService employeeService;

    public  WorkDayService(WorkDayRepository  workDayRepository, EmployeeService employeeService) {
        this.workDayRepository = workDayRepository;
        this.employeeService = employeeService;
    }

    public List<WorkDay> findAll() {
        return workDayRepository.findAll();
    }

    public WorkDay findById(Long id) {
        return workDayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Work day not found!"));
    }

    public List<WorkDay> findByEmployee(Long employeeId){
        return workDayRepository.findByEmployee(employeeService.findById(employeeId));
    }

    @Transactional
    public WorkDay createNew(WorkDayDto workDayDto) {
        WorkDay workDay = new WorkDay();
        setFields(workDay, workDayDto);
        return workDayRepository.save(workDay);
    }

    @Transactional
    public WorkDay update (WorkDayDto workDayDto, Long id) {
        Optional<WorkDay> workDayToUpdate = workDayRepository.findById(id);
        workDayToUpdate.orElseThrow(() -> new EntityNotFoundException("Work day not found!"));
        WorkDay workDay = workDayToUpdate.get();
        setFields(workDay, workDayDto);
        return workDayRepository.save(workDay);
    }

    @Transactional
    public void deleteByWorkDayId (Long id){
        workDayRepository.deleteById(id);
    }

    private WorkDay setFields (WorkDay workDay, WorkDayDto workDayDto){
        if (workDayDto.getFromWorkTime() != null) {
            workDay.setFromWorkTime(workDayDto.getFromWorkTime());
        }
        if (workDayDto.getToWorkTime() != null){
            workDay.setToWorkTime(workDayDto.getToWorkTime());
        }
        if (workDayDto.getEmployeeId() != null && employeeService.findById(workDayDto.getEmployeeId()) != null){
            workDay.setEmployee(employeeService.findById(workDayDto.getEmployeeId()));
        }
        return workDay;
    }

}
