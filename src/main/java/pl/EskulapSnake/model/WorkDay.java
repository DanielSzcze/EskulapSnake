package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class WorkDay {

    @Id
    @Column
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Employee employee;

    @Column
    private LocalDateTime from;

    @Column
    private LocalDateTime to;

}
