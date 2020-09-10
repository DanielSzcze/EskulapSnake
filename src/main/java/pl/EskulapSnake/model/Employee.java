package pl.EskulapSnake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<WorkDay> workDays;

}