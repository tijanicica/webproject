package com.tim23.webproject.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autor")
@PrimaryKeyJoinColumn(name = "autor_id")
public class Autor extends Korisnik {

    private boolean aktivan;
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Set<Knjiga> getSpisakKnjiga() {
        return spisakKnjiga;
    }

    public void setSpisakKnjiga(Set<Knjiga> spisakKnjiga) {
        this.spisakKnjiga = spisakKnjiga;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "aktivan=" + aktivan +
                ", spisakKnjiga=" + spisakKnjiga +
                '}';
    }
}
