package pl.EskulapSnake.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.EskulapSnake.model.*;
import pl.EskulapSnake.repository.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Tu możesz zainicializować dane do bazy by sprawdzić to w postmanie
 * Wstrzyknij swoje repo lub/i serwis i w run działaj do oporu
 */
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntryRepository entryRepository;
    private final ApplicationContext applicationContext;
    private final WorkDayRepository workDayRepository;
    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;
    private final VisitTypeRepository visitTypeRepository;
    private boolean alreadySetup = false;

    @Autowired
    public DataInitializer(
            VisitTypeRepository visitTypeRepository,
            PatientRepository patientRepository,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            EntryRepository entryRepository,
            ApplicationContext applicationContext,
            WorkDayRepository workDayRepository,
            EmployeeRepository employeeRepository) {
        this.visitTypeRepository = visitTypeRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.entryRepository = entryRepository;
        this.applicationContext = applicationContext;
        this.workDayRepository = workDayRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        List<Role> roles = new ArrayList<>();
        roles.add(createRoleIfNotFound("ROLE_ADMIN"));
        roles.add(createRoleIfNotFound("ROLE_MANAGER"));
        roles.add(createRoleIfNotFound("ROLE_PHYSICIAN"));
        roles.add(createRoleIfNotFound("ROLE_RECORDER"));
        roles.add(createRoleIfNotFound("ROLE_PATIENT"));
        roles.add(createRoleIfNotFound("ROLE_EMPLOYEE"));

        List<Role> adminRoles = roleRepository.findAll();
        User user = new User();
        user.setUsername(applicationContext.getEnvironment().getProperty("admin.username"));
        user.setPassword(passwordEncoder.encode(applicationContext.getEnvironment().getProperty("admin.passwd")));
        user.setEmail("eskulapsnake@gmail.com");
        user.setRoles(adminRoles);
        user.setEnabled(true);
        userRepository.save(user);

        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry();
            entry.setRecommendations("bla" + i);
            entry.setExamination("lol" + i);
            entry.setLocalDateTime(LocalDateTime.of(2020, 9, 8, 11, 15));
            Entry entry1 = new Entry();
            entry1.setRecommendations("bla" + i);
            entry1.setExamination("lol" + i);
            entry1.setLocalDateTime(LocalDateTime.of(2020, 6, 8, 11, 15));


            Patient patient = new Patient();
            Patient patient1 = new Patient();
            patient.setFirstName("Patient name");
            patient.setLastName("lastname" + i);
            patient1.setFirstName("loki" + i);
            patient1.setLastName("ppp" + i);

            WorkDay workDay = new WorkDay();
            WorkDay workDay1 = new WorkDay();
            workDay.setFromWorkTime(LocalDateTime.of(2020, 9, 8, 10, 00));
            workDay1.setFromWorkTime(LocalDateTime.of(2020, 6, 8, 8, 00));
            workDay.setToWorkTime((LocalDateTime.of(2020, 9, 8, 18, 00)));
            workDay1.setToWorkTime((LocalDateTime.of(2020, 6, 8, 16, 00)));

            User dummyUser = new User();
            dummyUser.setUsername("user" + i);
            dummyUser.setPassword(passwordEncoder.encode("password" + i));
            dummyUser.setEmail("email" + i);
            dummyUser.setEnabled(true);
            dummyUser.setRoles(roles);

            Employee employee = new Employee();
            employee.setFirstName("firstName" + i);
            employee.setLastName("lastName" + i);
            employee.setUser(dummyUser);
            entry.setEmployee(employee);
            entry1.setEmployee(employee);


            workDay.setEmployee(employee);
            workDay1.setEmployee(employee);
            employeeRepository.save(employee);
            workDayRepository.save(workDay);
            workDayRepository.save(workDay1);
            entryRepository.save(entry);
            entryRepository.save(entry1);
            Role role = new Role();
            role.setName("role "+ i);
            roleRepository.save(role);


            patient.getEntries().add(entry);
            patient1.getEntries().add(entry1);
            patientRepository.save(patient);
            patientRepository.save(patient1);

            VisitType visitType = new VisitType();
            visitType.setName("VisitType" + " " + i);
            visitTypeRepository.save(visitType);

            Set<Entry> entries = new HashSet<>();
            for (int j = 0; j < 5; j++) {
                Entry e = new Entry();
                e.setLocalDateTime(LocalDateTime.now());
                e.setExamination("zdfgdfgdfg" + j);
                e.setRecommendations("sdnjfhsdjfsdkf" + j);
                e.setEmployee(employee);
                e.setVisitType(visitType);
                entries.add(e);
            }
            patient.setEntries(entries);
            Random random = new Random();
            patient.setPesel(String.valueOf(random.nextInt(10000000)) + String.valueOf(random.nextInt(100)));
            patientRepository.save(patient);


        }
        alreadySetup = true;
    }

    private Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
