package com.tim23.webproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Polica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "polica_id")
    private Long id;
    private String naziv;
    private boolean primarna;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StavkaPolice> stavkaPolice = new ArrayList<>();

    public Polica(){}
    public Polica(String naziv, boolean primarna, List<StavkaPolice> stavkePolice) {
        this.naziv = naziv;
        this.primarna = primarna;
        this.stavkaPolice = stavkePolice;
    }

}
