package pl.EskulapSnake.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class VisitType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String name;
}
