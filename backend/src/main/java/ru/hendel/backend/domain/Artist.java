package ru.hendel.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @ManyToOne
    @JoinColumn(name = "countryId", referencedColumnName = "id")
    private Countries country;

    @Column(name = "age", length = 45)
    private String age;

    @OneToMany(mappedBy = "artist")
    private List<Painting> paintings;

}
