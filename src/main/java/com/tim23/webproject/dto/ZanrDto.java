package com.tim23.webproject.dto;
import com.tim23.webproject.entity.Zanr;
import lombok.*;

@Getter
@Setter
public class ZanrDto {

    private String naziv;

    public ZanrDto(String naziv) {
        this.naziv = naziv;
    }

    public ZanrDto(Zanr zanr) {
        this.naziv = zanr.getNaziv();
    }
}
