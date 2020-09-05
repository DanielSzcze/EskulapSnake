package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String pessel;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> role;

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<WorkTime> workTime;


}
