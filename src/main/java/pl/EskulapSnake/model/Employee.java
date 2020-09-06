package pl.EskulapSnake.model;

import lombok.Data;
import pl.EskulapSnake.dto.EmployeeDto;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String pesel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<WorkDay> workDays;

}