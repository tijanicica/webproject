package com.tim23.webproject.service;

import com.tim23.webproject.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.tim23.webproject.entity.Autor;
import com.tim23.webproject.entity.Status;
import com.tim23.webproject.entity.ZahtevZaAktivacijuNalogaAutora;
import com.tim23.webproject.repository.AutorRepository;
import com.tim23.webproject.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZahtevZaAktivacijuNalogaService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AutorRepository autorRepository;

    public void podnesiZahtevZaAktivacijuAutora(Long autorId, ZahtevZaAktivacijuNalogaAutoraDto zahtevDto) {
        Autor autor = autorRepository.findById(autorId).orElseThrow(() -> new EntityNotFoundException("Zahtev sa datim ID-om nije pronadjen."));

        ZahtevZaAktivacijuNalogaAutora zahtev = new ZahtevZaAktivacijuNalogaAutora(zahtevDto);
        zahtev.setAutor(autor);
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

    public void odbijZahtev(Long zahtevId) {
        ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findById(zahtevId).orElseThrow(() -> new EntityNotFoundException("Zahtev sa datim ID-om nije pronadjen."));

        zahtev.setStatus(Status.ODBIJEN);
        String emailBody = "Vas zahtev za aktivaciju naloga autora je odbijen.";
        String emailSubject = "Obavestenje o odbijanju zahteva";

        emailService.sendEmail(zahtev.getEmail(), emailSubject, emailBody);

    }

    public void prihvatiZahtev(Long zahtevId) {
        ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findById(zahtevId).orElseThrow(() -> new EntityNotFoundException("Zahtev sa datim ID-om nije pronadjen."));

        zahtev.setStatus(Status.ODOBREN);
        Autor autor = zahtev.getAutor();
        autor.setAktivan(true);
        String novaLozinka = autor.getLozinka();
        //kreiraj primarne police, uradio nikola, dodati
        String emailBody = "Vas zahtev za aktivaciju naloga autora je prihvacen." +
                           "Lozinka vaseg naloga je : " + novaLozinka;
        String emailSubject = "Obavestenje o prihvatanju zahteva";

        emailService.sendEmail(zahtev.getEmail(), emailSubject, emailBody);

    }
}
