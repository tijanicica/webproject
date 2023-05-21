package com.tim23.webproject.service;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public AutorDto kreirajAutora(AutorDto autorDto, String mejlAdresa, String lozinka) {

        if (!autorDto.getUloga().equals(Uloga.CITALAC)) {
            throw new IllegalStateException("Citalac ne moze da postane autor.");
        }
        if (!autorDto.getUloga().equals(Uloga.ADMINISTRATOR)) {
            throw new IllegalStateException("Nije moguce kreirati administratora. Oni se ucitavaju iskljucivo iz baze.");
        }

        Autor autor = new Autor();
        autor.setIme(autorDto.getIme());
        autor.setPrezime(autorDto.getPrezime());
        autor.setKorisnickoIme(autorDto.getKorisnickoIme());
        autor.setMejlAdresa(mejlAdresa);
        autor.setLozinka(lozinka);
        autor.setDatumRodjenja(autorDto.getDatumRodjenja());
        autor.setUloga(autorDto.getUloga());
        autor.setProfilnaSlika(autorDto.getProfilnaSlika());
        autor.setAktivan(autorDto.isAktivan());
        //convert
        List<PolicaDto> policaDtoList = autorDto.getPolice();
        List<Polica> policaList = new ArrayList<>();

        for (PolicaDto policaDto : policaDtoList) {
            Polica polica = new Polica();
            polica.setPrimarna(policaDto.isPrimarna());
            polica.setNaziv(policaDto.getNaziv());

            //convert StvakaPolice
            List<StavkaPoliceDto> stavkaPolicedtoList = policaDto.getStavkaPolice();
            List<StavkaPolice> stavkaPoliceList = new ArrayList<>();

            for (StavkaPoliceDto stavkaPolicedto : stavkaPolicedtoList) {
                StavkaPolice stavkaPolice = new StavkaPolice();

                // convert Recenzije
                RecenzijaBezKorisnikaDto recenzijedto = stavkaPolicedto.getRecenzija();
                Recenzija recenzija = new Recenzija();
                recenzija.setOcena(recenzijedto.getOcena());
                recenzija.setTekst(recenzijedto.getTekst());
                recenzija.setDatumRecenzije(recenzijedto.getDatumRecenzije());

                // convert Knjige
                KnjigaDto knjigaDto = stavkaPolicedto.getKnjiga();
                Knjiga knjiga = new Knjiga();
                knjiga.setNaslov(knjigaDto.getNaslov());
                knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
                knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
                knjiga.setBrojStrana(knjigaDto.getBrojStrana());
                knjiga.setOpis(knjigaDto.getOpis());
                knjiga.setOcena(knjigaDto.getOcena());

                // convert Zanr
                ZanrDto zanrdto = knjigaDto.getZanr();
                Zanr zanr = new Zanr();
                zanr.setNaziv(zanrdto.getNaziv());
                knjiga.setZanr(zanr);

                // Setovanje konvertovanih objekata
                stavkaPolice.setRecenzija(recenzija);
                stavkaPolice.setKnjiga(knjiga);
                stavkaPoliceList.add(stavkaPolice);
            }

            // Setovanje konvertovane liste StavkaPolice
            polica.setStavkaPolice(stavkaPoliceList);

            policaList.add(polica);
        }

        // Setovanje konvertovane liste Polica
        autor.setPolice(policaList);

        // convert spisak knjiga
        //autor.setSpisakKnjiga(autorDto.getSpisakKnjiga());
        Set<KnjigaDto> knjigeDtoSet = autorDto.getSpisakKnjiga();
        Set<Knjiga> knjigeSet = new HashSet<>();
        for (KnjigaDto knjigaDto : knjigeDtoSet) {
            ZanrDto zanrDto = knjigaDto.getZanr();
            Zanr zanr = new Zanr(zanrDto.getNaziv());
            Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getNaslovnaFotografija(), knjigaDto.getDatumObjavljivanja(), knjigaDto.getBrojStrana(), knjigaDto.getOpis(), knjigaDto.getOcena(), zanr);
            knjigeSet.add(knjiga);
        }
        autor.setSpisakKnjiga(knjigeSet);

        autorRepository.save(autor);

        return convertToDto(autor);
    }

    private AutorDto convertToDto(Autor autor) {
        AutorDto autorDto = new AutorDto();
        autorDto.setIme(autor.getIme());
        autorDto.setPrezime(autor.getPrezime());
        autorDto.setKorisnickoIme(autor.getKorisnickoIme());
        autorDto.setDatumRodjenja(autor.getDatumRodjenja());
        autorDto.setUloga(autor.getUloga());
        autorDto.setProfilnaSlika(autor.getProfilnaSlika());
        autorDto.setAktivan(autor.isAktivan());

        // Convert policaList
        List<PolicaDto> policaDtoList = new ArrayList<>();
        for (Polica polica : autor.getPolice()) {
            PolicaDto policaDto = new PolicaDto();
            policaDto.setPrimarna(polica.isPrimarna());
            policaDto.setNaziv(polica.getNaziv());

            // Convert stavkaPoliceList
            List<StavkaPoliceDto> stavkaPolicedtoList = new ArrayList<>();
            for (StavkaPolice stavkaPolice : polica.getStavkaPolice()) {
                StavkaPoliceDto stavkaPolicedto = new StavkaPoliceDto();

                // Convert recenzija
                Recenzija recenzija = stavkaPolice.getRecenzija();
                RecenzijaBezKorisnikaDto recenzijaDto = new RecenzijaBezKorisnikaDto();
                recenzijaDto.setOcena(recenzija.getOcena());
                recenzijaDto.setTekst(recenzija.getTekst());
                recenzijaDto.setDatumRecenzije(recenzija.getDatumRecenzije());

                // Convert knjiga
                Knjiga knjiga = stavkaPolice.getKnjiga();
                KnjigaDto knjigaDto = new KnjigaDto();
                knjigaDto.setNaslov(knjiga.getNaslov());
                knjigaDto.setNaslovnaFotografija(knjiga.getNaslovnaFotografija());
                knjigaDto.setDatumObjavljivanja(knjiga.getDatumObjavljivanja());
                knjigaDto.setBrojStrana(knjiga.getBrojStrana());
                knjigaDto.setOpis(knjiga.getOpis());
                knjigaDto.setOcena(knjiga.getOcena());

                // Convert zanr
                Zanr zanr = knjiga.getZanr();
                ZanrDto zanrDto = new ZanrDto();
                zanrDto.setNaziv(zanr.getNaziv());
                knjigaDto.setZanr(zanrDto);

                // Set converted objects
                stavkaPolicedto.setRecenzija(recenzijaDto);
                stavkaPolicedto.setKnjiga(knjigaDto);
                stavkaPolicedtoList.add(stavkaPolicedto);
            }

            // Set converted stavkaPoliceList
            policaDto.setStavkaPolice(stavkaPolicedtoList);
            policaDtoList.add(policaDto);
        }

        // Set converted policaList
        autorDto.setPolice(policaDtoList);

        // Convert spisakKnjiga
        Set<KnjigaDto> knjigeDtoSet = new HashSet<>();
        for (Knjiga knjiga : autor.getSpisakKnjiga()) {
            Zanr zanr = knjiga.getZanr();
            ZanrDto zanrDto = new ZanrDto();
            zanrDto.setNaziv(zanr.getNaziv());
            KnjigaDto knjigaDto = new KnjigaDto();
            knjigaDto.setNaslov(knjiga.getNaslov());
            knjigaDto.setNaslovnaFotografija(knjiga.getNaslovnaFotografija());
            knjigaDto.setDatumObjavljivanja(knjiga.getDatumObjavljivanja());
            knjigaDto.setBrojStrana(knjiga.getBrojStrana());
            knjigaDto.setOpis(knjiga.getOpis());
            knjigaDto.setOcena(knjiga.getOcena());
            knjigaDto.setZanr(zanrDto);
            knjigeDtoSet.add(knjigaDto);
        }
        autorDto.setSpisakKnjiga(knjigeDtoSet);

        return autorDto;
    }


}
