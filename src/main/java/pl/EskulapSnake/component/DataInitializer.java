package pl.EskulapSnake.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.EskulapSnake.model.*;
import pl.EskulapSnake.repository.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationRunner {


    @Autowired
    EntryRepository entryRepository;
    @Autowired
    WorkDayRepository workDayRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    VisitTypeRepository visitTypeRepository;
    @Autowired
    PatientRepository patientRepository;

    @Override
    public void run(ApplicationArguments args) {
        for (int i =0; i<10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla");
            entry.setExamination("lol");

            entryRepository.save(entry);

            Employee employee = new Employee();
//            employee.setId(Long.valueOf(i));
            employee.setFirstName("firstName" + i);
            employee.setLastName("lastName" + i);
            employeeRepository.save(employee);

            WorkDay workDay = new WorkDay();
            workDay.setEmployee(employee);
            workDay.setFromWorkTime(LocalDateTime.now());
            workDay.setToWorkTime(LocalDateTime.now().plusHours(8l));
            workDayRepository.save(workDay);

            VisitType visitType = new VisitType();
            visitType.setName("VisitType" + " " + i);
            visitTypeRepository.save(visitType);


            Patient patient = new Patient();
            patient.setFirstName("Patient name");
            patient.setLastName("lastname" + i);
            Set<Entry> entries = new HashSet<>();
            for (int j = 0; j < 5; j++) {
                Entry e = new Entry();
                e.setLocalDateTime(LocalDateTime.now());
                e.setExamination("zdfgdfgdfg" + j);
                e.setRecommendations("sdnjfhsdjfsdkf" + j);
                e.setEmployee(employee);
                e.setVisitType(visitType);
                entries.add(e);
//                entryRepository.save(e);
            }
            patient.setEntries(entries);
            Random random = new Random();
            patient.setPesel(String.valueOf(random.nextInt(10000000)) + String.valueOf(random.nextInt(100)));
            patientRepository.save(patient);

        }
    }
}
