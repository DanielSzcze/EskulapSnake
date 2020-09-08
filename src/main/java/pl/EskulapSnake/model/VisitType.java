package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "visit_types")
@Data
public class VisitType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private  String name;

}
