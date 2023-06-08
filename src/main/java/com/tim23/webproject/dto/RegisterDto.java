package com.tim23.webproject.dto;

import com.tim23.webproject.entity.Uloga;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String mejlAdresa;
    private String lozinka;
    private String ponovljenaLozinka;

    private Uloga uloga;

    public RegisterDto(String ime, String prezime, String korisnickoIme, String mejlAdresa, String lozinka, String ponovljenaLozinka, Uloga uloga) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mejlAdresa = mejlAdresa;
        this.lozinka = lozinka;
        this.ponovljenaLozinka = ponovljenaLozinka;
        this.uloga = uloga;
    }

    public RegisterDto() {}
}
