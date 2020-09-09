package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_days")
@Data
public class WorkDay {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @Column
    private LocalDateTime fromWorkTime;

    @Column
    private LocalDateTime toWorkTime;

}
