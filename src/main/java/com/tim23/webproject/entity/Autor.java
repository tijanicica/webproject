package com.tim23.webproject.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "autor")
@PrimaryKeyJoinColumn(name = "autor_id")
public class Autor extends Korisnik {

    private boolean aktivan;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "spisak_knjiga")
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

}
