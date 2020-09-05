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
    @Lob
    private String text;

    private LocalDateTime localDateTime;

    private String examination;

    @OneToOne
    @JoinColumn
    private Employee employee;

    @OneToOne
    @JoinColumn
    private VisitType visitType;


}
