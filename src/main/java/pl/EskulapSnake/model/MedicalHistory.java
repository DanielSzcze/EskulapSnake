package pl.EskulapSnake.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class MedicalHistory {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entryList;

    @OneToOne
    @JoinColumn
    private Specialization specialization;
}
