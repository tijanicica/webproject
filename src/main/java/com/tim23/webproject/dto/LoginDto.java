package com.tim23.webproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    private String mejlAdresa;

    private String lozinka;

    public LoginDto(String mejlAdresa, String lozinka) {
        this.mejlAdresa = mejlAdresa;
        this.lozinka = lozinka;
    }

    public LoginDto() {}

}