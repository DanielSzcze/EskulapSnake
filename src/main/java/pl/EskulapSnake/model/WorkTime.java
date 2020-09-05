package pl.EskulapSnake.model;

import lombok.Data;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "from", nullable = false)
    private LocalDateTime fromLocalDateTime;

    @Column(name = "to", nullable = false)
    private LocalDateTime toLocalDateTime;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;



}
