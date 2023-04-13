package com.tim23.webproject.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stavkapolice")
public class StavkaPolice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stavkaPoliceId")
    private Long stavkaPoliceId;

    @OneToMany(mappedBy = "stavkapolice", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recenzija> recenzija = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "knjiga_ISBN", referencedColumnName = "stavkaPoliceId")
    private Knjiga knjiga;

    public Long getStavkaPoliceId() {
        return stavkaPoliceId;
    }

    public void setStavkaPoliceId(Long stavkaPoliceId) {
        this.stavkaPoliceId = stavkaPoliceId;
    }

    public List<Recenzija> getRecenzija() {
        return recenzija;
    }

    public void setRecenzija(List<Recenzija> recenzija) {
        this.recenzija = recenzija;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }
}
