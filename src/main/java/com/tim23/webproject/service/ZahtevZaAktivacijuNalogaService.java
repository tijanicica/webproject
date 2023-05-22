package com.tim23.webproject.service;

import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import com.tim23.webproject.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZahtevZaAktivacijuNalogaService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    public void podnesiZahtevZaAktivacijuAutora(ZahtevZaAktivacijuNalogaAutoraDto zahtevDto) {
        ZahtevZaAktivacijuNalogaAutora zahtev = new ZahtevZaAktivacijuNalogaAutora(zahtevDto);
         zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev);
    }
}
