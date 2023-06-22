package com.tim23.webproject.service;

import com.tim23.webproject.dto.*;
import com.tim23.webproject.entity.*;
import com.tim23.webproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.*;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;
    @Autowired
    private KnjigaRepository knjigaRepository;
    @Autowired
    private ZanrRepository zanrRepository;
    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private AutorRepository autorRepository;

    //NIKOLA
    public void  dodajPrimarnePoliceKorisniku(Korisnik korisnik) {
        if (korisnik.getUloga() == Uloga.CITALAC || korisnik.getUloga() == Uloga.AUTOR) {
            Polica wantToRead = new Polica("Want to Read", true, new ArrayList<>());
            Polica currentlyReading = new Polica("Currently Reading", true, new ArrayList<>());
            Polica read = new Polica("Read", true, new ArrayList<>());

            korisnik.getPolice().add(wantToRead);
            policaRepository.save(wantToRead);
            korisnik.getPolice().add(currentlyReading);
            policaRepository.save(currentlyReading);
            korisnik.getPolice().add(read);
            policaRepository.save(read);
        }
    }
    public Korisnik login(String mejlAdresa, String lozinka) {
        Korisnik korisnik = korisnikRepository.getByMejlAdresa(mejlAdresa);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        dodajPrimarnePoliceKorisniku(korisnik);
        korisnikRepository.save(korisnik);

        return  korisnik;
    }

    public void register(RegisterDto registerDto) throws Exception {
        if (korisnikRepository.existsByKorisnickoIme(registerDto.getKorisnickoIme())) {
            throw new Exception("Korisničko ime već postoji.");
        }

        if (korisnikRepository.existsByMejlAdresa(registerDto.getMejlAdresa())) {
            throw new Exception("Email adresa već postoji.");
        }

        if (!registerDto.getLozinka().equals(registerDto.getPonovljenaLozinka())) {
            throw new Exception("Lozinke se ne podudaraju.");
        }

        Korisnik korisnik = new Korisnik();
        korisnik.setIme(registerDto.getIme());
        korisnik.setPrezime(registerDto.getPrezime());
        korisnik.setKorisnickoIme(registerDto.getKorisnickoIme());
        korisnik.setMejlAdresa(registerDto.getMejlAdresa());
        korisnik.setLozinka(registerDto.getLozinka());
        korisnik.setUloga(Uloga.CITALAC);

        korisnikRepository.save(korisnik);
    }

    public List<KorisnikDto> getAllKorisnik() {
        List<Korisnik> korisnici = korisnikRepository.findAll();
        List<KorisnikDto> dtos = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return dtos;

    }

    public List<KorisnikDto> searchByIme(String ime) {
        List<Korisnik> korisnici= korisnikRepository.findByIme(ime);

        List<KorisnikDto> dtos = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<KorisnikDto> searchByPrezime(String prezime) {
        List<Korisnik> korisnici= korisnikRepository.findByPrezime(prezime);

        List<KorisnikDto> dtos = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return dtos;
    }

    public KorisnikDto searchByKorisnickoIme(String korisnickoIme) {
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(korisnickoIme);

        KorisnikDto korisnikDto = new KorisnikDto(korisnik);

        return korisnikDto;

    }

    public List<KorisnikDto> searchByUloga(String uloga) {
        Uloga ulogaEnum = Uloga.valueOf(uloga);
        List<Korisnik> korisnici = korisnikRepository.findByUloga(ulogaEnum);
        List<KorisnikDto> dtos = new ArrayList<>();
        for (Korisnik korisnik : korisnici) {
            KorisnikDto dto = new KorisnikDto(korisnik);
            dtos.add(dto);
        }
        return dtos;

    }


    public List<Polica> getPolicePrijavljenogKorisnika(Long korisnikId) {
        Optional<Korisnik> nadjeniKorisnik = korisnikRepository.findById(korisnikId);
        if (nadjeniKorisnik.isPresent()){
            return korisnikRepository.findPoliceByKorisnikId(korisnikId);
        }
        return null;
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

    public AutorDto kreirajAutora(AutorDto autorDto, String mejlAdresa, String lozinka) {

        if (autorDto.getUloga().equals(Uloga.CITALAC)) {
            throw new IllegalStateException("Citalac ne moze da postane autor.");
        }
        if (autorDto.getUloga().equals(Uloga.ADMINISTRATOR)) {
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
        autor.setAktivan(false);
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
                recenzijaRepository.save(recenzija);

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
                zanrRepository.save(zanr);
                knjigaRepository.save(knjiga);

                // Setovanje konvertovanih objekata
                stavkaPolice.setRecenzija(recenzija);
                stavkaPolice.setKnjiga(knjiga);
                stavkaPoliceList.add(stavkaPolice);
                stavkaPoliceRepository.save(stavkaPolice);
            }

            // Setovanje konvertovane liste StavkaPolice
            polica.setStavkaPolice(stavkaPoliceList);

            policaList.add(polica);
            policaRepository.save(polica);
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
            zanrRepository.save(zanr);
            Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getNaslovnaFotografija(), knjigaDto.getDatumObjavljivanja(), knjigaDto.getBrojStrana(), knjigaDto.getOpis(), knjigaDto.getOcena(), zanr);
            knjigeSet.add(knjiga);
            knjigaRepository.save(knjiga);
        }

        autor.setSpisakKnjiga(knjigeSet);

        korisnikRepository.save(autor);

        return convertToDto(autor);
    }
    public Korisnik nadjiKorisnikaPoMejlAdresi(String mejlAdresa) {
        return korisnikRepository.findByMejlAdresa(mejlAdresa);
    }


    public boolean imaUloguAutora(String mejlAdresa) {
        Korisnik korisnik = korisnikRepository.findByMejlAdresa(mejlAdresa);
        return korisnik != null && korisnik.getUloga() == Uloga.AUTOR;
    }

    public void azurirajProfilAutora(Long autorId, AutorDto autorDto) {
        Optional<Korisnik> optionalKorisnik = korisnikRepository.findById(autorId);
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            if (korisnik.getUloga().equals(Uloga.AUTOR)) {
                Autor autor = (Autor) korisnik;
                if (!autor.isAktivan()) {

                    autor.setIme(autorDto.getIme());
                    autor.setPrezime(autorDto.getPrezime());
                    autor.setDatumRodjenja(autorDto.getDatumRodjenja());
                    autor.setProfilnaSlika(autorDto.getProfilnaSlika());
                    autor.setOpis(autorDto.getOpis());

                    autorRepository.save(autor);
                    korisnikRepository.save(autor);
                } else {
                    throw new IllegalArgumentException("Nalog autora je aktivan. Samo neaktivirani nalozi mogu biti azurirani.");
                }
            } else {
                throw new IllegalArgumentException("Korisnik sa zadatim ID-jem nije autor.");
            }
        } else {
            throw new NoSuchElementException("Korisnik sa zadatim ID-jem nije pronađen.");
        }
    }


    public void dodajAutora(AutorDto autorDto) {
        Autor autor = new Autor();
        autor.setIme(autorDto.getIme());
        autor.setPrezime(autorDto.getPrezime());
        autor.setDatumRodjenja(autorDto.getDatumRodjenja());
        autor.setProfilnaSlika(autorDto.getProfilnaSlika());
        autor.setOpis(autorDto.getOpis());
        autor.setAktivan(false);
        autor.setUloga(Uloga.AUTOR);
        autor.setSpisakKnjiga(null);
        autorRepository.save(autor);
        korisnikRepository.save(autor);
    }
/*
    public void azurirajProfil(Long korisnikId, String ime, String prezime, LocalDate datumRodjenja, String profilnaSlika,
                               String opis, String mejlAdresa, String novaLozinka, String trenutnaLozinka) {
        Korisnik korisnik = korisnikRepository.findById(korisnikId)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik sa ID-jem " + korisnikId + " nije pronađen."));

        if (trenutnaLozinka != null && novaLozinka != null) {
            if (!trenutnaLozinka.equals(korisnik.getLozinka())) {
                throw new IllegalArgumentException("Pogrešna stara lozinka");
            }
            korisnik.setLozinka(novaLozinka);
        }

        korisnik.setIme(ime != null ? ime : korisnik.getIme());
        korisnik.setPrezime(prezime != null ? prezime : korisnik.getPrezime());
        korisnik.setDatumRodjenja(datumRodjenja != null ? datumRodjenja : korisnik.getDatumRodjenja());
        korisnik.setProfilnaSlika(profilnaSlika != null ? profilnaSlika : korisnik.getProfilnaSlika());
        korisnik.setOpis(opis != null ? opis : korisnik.getOpis());
        korisnik.setMejlAdresa(mejlAdresa != null ? mejlAdresa : korisnik.getMejlAdresa());

        korisnikRepository.save(korisnik);
    }
*/
public void azurirajProfil(Korisnik korisnikB, String ime, String prezime, LocalDate datumRodjenja, String profilnaSlika,
                           String opis, String mejlAdresa, String novaLozinka, String trenutnaLozinka) {
    Korisnik korisnik = korisnikRepository.findById(korisnikB.getId())
            .orElseThrow(() -> new EntityNotFoundException("Korisnik sa ID-jem " + korisnikB.getId() + " nije pronađen."));

    if (trenutnaLozinka != null && novaLozinka != null) {
        if (!trenutnaLozinka.equals(korisnik.getLozinka())) {
            throw new IllegalArgumentException("Pogrešna stara lozinka");
        }
        korisnik.setLozinka(novaLozinka);
    }

    korisnik.setIme(ime != null ? ime : korisnik.getIme());
    korisnik.setPrezime(prezime != null ? prezime : korisnik.getPrezime());
    korisnik.setDatumRodjenja(datumRodjenja != null ? datumRodjenja : korisnik.getDatumRodjenja());
    korisnik.setProfilnaSlika(profilnaSlika != null ? profilnaSlika : korisnik.getProfilnaSlika());
    korisnik.setOpis(opis != null ? opis : korisnik.getOpis());
    korisnik.setMejlAdresa(mejlAdresa != null ? mejlAdresa : korisnik.getMejlAdresa());

    korisnikRepository.save(korisnik);
}
}
