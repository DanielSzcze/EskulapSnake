package pl.EskulapSnake.model;



import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Entry {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @Column
    private String text;
    @Column
    private LocalDateTime localDateTime;

}
