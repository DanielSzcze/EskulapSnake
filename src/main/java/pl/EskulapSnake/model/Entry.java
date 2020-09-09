package pl.EskulapSnake.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
@Data
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime localDateTime;
    @Lob
    private String examination;
    @Lob
    private String recommendations;

    @OneToOne
    @JoinColumn
    private Employee employee;

    @OneToOne
    @JoinColumn
    private VisitType visitType;

}
