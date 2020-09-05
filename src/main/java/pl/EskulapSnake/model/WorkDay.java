package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_day")
@Data
public class WorkDay {

    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column
    private LocalDateTime from;

    @Column
    private LocalDateTime to;

}
