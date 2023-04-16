package com.tim23.webproject.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "stavkapolice")
public class StavkaPolice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavka_police_id")
    private Long stavkaPoliceId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzija> recenzija = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "knjiga_ISBN")
    private Knjiga knjiga;

}
