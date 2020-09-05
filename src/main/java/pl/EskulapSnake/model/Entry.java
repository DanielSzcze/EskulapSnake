package pl.EskulapSnake.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
@Data
public class Entry {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Lob
    private String text;
    @Column
    private LocalDateTime localDateTime;

    @Column
    private String examination;

    @OneToOne
    @JoinColumn
    private Employee employee;

    @OneToOne
    @JoinColumn
    private VisitType visitType;


}
