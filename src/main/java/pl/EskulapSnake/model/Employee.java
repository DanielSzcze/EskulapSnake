package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
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

    @Column
    private String pesel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "employee")
    List<WorkDay> workDays;
}