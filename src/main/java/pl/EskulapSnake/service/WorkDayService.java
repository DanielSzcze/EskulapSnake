package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.dto.WorkDayDto;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.WorkDay;
import pl.EskulapSnake.repository.WorkDayRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    public List<WorkDay> findByEmployeeIdAndDate(Long employeeId, String monthAndYear) {
        String[] dateInArray = monthAndYear.split("\\.");
        int month = Integer.parseInt(dateInArray[0]);
        int year = Integer.parseInt(dateInArray[1]);
        if(year<100) year+=2000;
        LocalDateTime beginOfMonth = this.getBeginOfMonth(month, year);
        LocalDateTime endOfMonth = this.getEndOfMonth(month, year);
        return workDayRepository.getByEmpIdAndTimeInterval(employeeId, beginOfMonth, endOfMonth);


    }
    public List<WorkDay> findByUserNameAndDate(String userName, String monthAndYear) {
        Employee employee = employeeService.findByUserName(userName);
        return this.findByEmployeeIdAndDate(employee.getId(), monthAndYear);


    }
    private LocalDateTime getBeginOfMonth(Integer month, Integer year) {
       LocalDateTime minDateOfMonth = LocalDateTime.of(year, month, 1, 00,00);
       return minDateOfMonth;

    }
    private LocalDateTime getEndOfMonth(Integer month, Integer year) {

        LocalDateTime maxDateOfMonth= LocalDateTime.of(year, month+1, 1,23,59).minusDays(1);
        return maxDateOfMonth;

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
        if (workDayDto.getEmployee() != null && employeeService.findById(workDayDto.getEmployee().getId())  != null){
            workDay.setEmployee(workDayDto.getEmployee());
        }
        else if(workDayDto.getEmployeeId() !=null) {
            Employee employee = employeeService.findById(workDayDto.getEmployeeId());
            workDay.setEmployee(employee);
        }
        return workDay;
    }


}
