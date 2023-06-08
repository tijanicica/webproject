package com.tim23.webproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tim23.webproject.entity.Polica;
import com.tim23.webproject.entity.StavkaPolice;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PolicaDto {

    private String naziv;
    private boolean primarna;

    private List<StavkaPoliceDto> stavkaPolice = new ArrayList<>();

    public PolicaDto(String naziv, boolean primarna, List<StavkaPoliceDto> stavkaPolice) {
        this.naziv = naziv;
        this.primarna = primarna;
        this.stavkaPolice = stavkaPolice;
    }

    public PolicaDto() {
    }

    public PolicaDto(Polica polica) {
        this.naziv = polica.getNaziv();
        this.primarna = polica.isPrimarna();

        this.stavkaPolice = new ArrayList<>();
        for (StavkaPolice stavka : polica.getStavkaPolice()) {
            this.stavkaPolice.add(new StavkaPoliceDto(stavka));
        }
    }


}
