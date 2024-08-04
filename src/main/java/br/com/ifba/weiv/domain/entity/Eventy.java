package br.com.ifba.weiv.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Timer;

import java.util.Date;

@Entity
@Table (name = "Eventy")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Eventy{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "date")
    private Date date;

    @Column(name = "hour")
    private String hour; // Use String or appropriate type

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "image")
    private String image;
}
