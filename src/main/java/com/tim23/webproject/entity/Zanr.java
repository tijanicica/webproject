package com.tim23.webproject.entity;

import com.tim23.webproject.dto.ZanrDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class Zanr implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "zanr_id")
    private Long id;
    private String naziv;

    public Zanr(String naziv) {
        this.naziv = naziv;
    }
    public Zanr(){}
}
