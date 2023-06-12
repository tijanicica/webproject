package com.tim23.webproject.dto;

import com.tim23.webproject.entity.Knjiga;
import com.tim23.webproject.entity.Recenzija;
import com.tim23.webproject.entity.StavkaPolice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StavkaPoliceDto {


    private RecenzijaBezKorisnikaDto recenzija;

    private KnjigaDto knjiga;

    public StavkaPoliceDto(RecenzijaBezKorisnikaDto recenzija, KnjigaDto knjiga) {
        this.recenzija = recenzija;
        this.knjiga = knjiga;
    }

    public StavkaPoliceDto() {}

    public StavkaPoliceDto(StavkaPolice stavkaPolice) {

        Recenzija recenzijaKnjige = stavkaPolice.getRecenzija();
        if (recenzijaKnjige != null) {
            this.recenzija = new RecenzijaBezKorisnikaDto(recenzijaKnjige);
        }

        Knjiga knjigaStavke = stavkaPolice.getKnjiga();
        if (knjigaStavke != null) {
            this.knjiga = new KnjigaDto(knjigaStavke);
        }
    }

    public void setKnjigaDto(KnjigaDto knjigaDto) {
        this.knjiga = knjigaDto;
    }

    public void setRecenzijaDto(RecenzijaBezKorisnikaDto recenzijaBezKorisnikaDto) {
        this.recenzija = recenzijaBezKorisnikaDto;
    }
}
