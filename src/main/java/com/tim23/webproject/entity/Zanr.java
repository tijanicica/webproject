package com.tim23.webproject.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Zanr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zanr_id")
    private Long zanrId;
    private String naziv;

    public Long getZanrId() {
        return zanrId;
    }

    public void setZanrId(Long zanrId) {
        this.zanrId = zanrId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "Zanr{" +
                "zanrId=" + zanrId +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
