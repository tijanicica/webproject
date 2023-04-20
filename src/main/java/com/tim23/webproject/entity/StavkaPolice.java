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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recenzija_id")
    private Recenzija recenzija;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knjiga_ISBN")
    private Knjiga knjiga;

}
