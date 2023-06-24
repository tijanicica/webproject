<template>
    <div>
      <h2>Popis profila korisnika</h2>
      <div v-for="korisnik in korisnici" :key="korisnik.korisnickoIme" class="profil">
        <!-- <div class="profilna-slika">
          <img :src="korisnik.profilnaSlika" alt="Profilna slika" />
        </div>  -->
        <div class="profilna-slika">
          <p> {{korisnik.profilnaSlika}} </p>
        </div>  

        <div class="informacije">
          <h3>{{ korisnik.ime }} {{ korisnik.prezime }}</h3>
          <p><strong>Korisničko ime:</strong> {{ korisnik.korisnickoIme }}</p>
          <p><strong>Datum rođenja:</strong> {{ formatDate(korisnik.datumRodjenja) }}</p>
          <p><strong>Opis:</strong> {{ korisnik.opis }}</p>
          <p><strong>Uloga:</strong> {{ korisnik.uloga }}</p>
          <h4>Police:</h4>
          <ul>
            <li v-for="polica in korisnik.police" :key="polica.naziv">
              {{ polica.naziv }} (Primarna: {{ polica.primarna }})
              <h5>Stavke police:</h5>
              <ul>
                <li v-for="stavka in polica.stavkaPolice" :key="stavka.recenzija.id">
                  <strong>Knjiga:</strong> {{ stavka.knjiga.naslov }}
                  <br />
                  <strong>Recenzija:</strong> Ocena: {{ stavka.recenzija.ocena }}, Tekst: {{ stavka.recenzija.tekst }}, Datum recenzije: {{ formatDate(stavka.recenzija.datumRecenzije) }}
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        korisnici: [],
      };
    },
    mounted() {
      this.getProfiliKorisnika();
    },
    methods: {
      getProfiliKorisnika() {
        fetch('http://localhost:9090/api/profili-korisnika')
          .then((response) => response.json())
          .then((data) => {
            this.korisnici = data;
          })
          .catch((error) => {
            console.error('Došlo je do pogreške prilikom dohvata profila korisnika:', error);
          });
      },
      formatDate(date) {
        // Formatiranje datuma prema željenom formatu (npr. DD.MM.GGGG.)
        // Prilagodite funkciju prema potrebama
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Date(date).toLocaleDateString('hr-HR', options);
      },
    },
  };
  </script>
  
  <style scoped>
  /* Stilizacija može biti prilagođena prema vašim potrebama */
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  
  .profil {
    display: flex;
    margin-bottom: 2rem;
  }
  
  .profilna-slika img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 50%;
    margin-right: 1rem;
  }
  
  .informacije h3 {
    margin-bottom: 0.5rem;
  }
  
  .informacije p {
    margin-bottom: 0.25rem;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
    margin-top: 0.5rem;
  }
  
  h4 {
    margin-bottom: 0.25rem;
  }
  
  h5 {
    margin-bottom: 0.25rem;
    margin-top: 1rem;
  }
  </style>
  