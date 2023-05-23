package com.tim23.webproject.service;

import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import com.tim23.webproject.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZahtevZaAktivacijuNalogaService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    public void podnesiZahtevZaAktivacijuAutora(ZahtevZaAktivacijuNalogaAutoraDto zahtevDto) {
        ZahtevZaAktivacijuNalogaAutora zahtev = new ZahtevZaAktivacijuNalogaAutora(zahtevDto);
         zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev);
    }


    public List<ZahtevZaAktivacijuNalogaAutoraDto> getAllZahtevi() {
        List<ZahtevZaAktivacijuNalogaAutora> zahtevi = zahtevZaAktivacijuNalogaAutoraRepository.findAll();

        List<ZahtevZaAktivacijuNalogaAutoraDto> dtos = new ArrayList<>();
            for (ZahtevZaAktivacijuNalogaAutora zahtev : zahtevi) {
                ZahtevZaAktivacijuNalogaAutoraDto dto = new ZahtevZaAktivacijuNalogaAutoraDto(zahtev);
                dtos.add(dto);
            }
            return dtos;
    }
}
