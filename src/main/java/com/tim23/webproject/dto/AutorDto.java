package com.tim23.webproject.dto;
import com.tim23.webproject.entity.Autor;
import com.tim23.webproject.entity.Knjiga;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AutorDto {

    private boolean aktivan;
    private Set<KnjigaDto> spisakKnjiga = new HashSet<>();

    public AutorDto(boolean aktivan, Set<KnjigaDto> spisakKnjiga) {
        this.aktivan = aktivan;
        this.spisakKnjiga = spisakKnjiga;
    }

    public AutorDto() {}

    public AutorDto(Autor autor) {
        this.aktivan = autor.isAktivan();
        this.spisakKnjiga = new HashSet<>();
        for (Knjiga knjiga : autor.getSpisakKnjiga()) {
            this.spisakKnjiga.add(new KnjigaDto(knjiga));
        }
    }
}
