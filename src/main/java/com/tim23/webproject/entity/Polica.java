package com.tim23.webproject.entity;



import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Polica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policaId;
    private String naziv;
    private boolean primarna;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StavkaPolice> stavkaPolice = new HashSet<>();

    public Long getPolicaId() {
        return policaId;
    }

    public void setPolicaId(Long policaId) {
        this.policaId = policaId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public boolean isPrimarna() {
        return primarna;
    }

    public void setPrimarna(boolean primarna) {
        this.primarna = primarna;
    }

    public Set<StavkaPolice> getStavkaPolice() {
        return stavkaPolice;
    }

    public void setStavkaPolice(Set<StavkaPolice> stavkaPolice) {
        this.stavkaPolice = stavkaPolice;
    }
}
