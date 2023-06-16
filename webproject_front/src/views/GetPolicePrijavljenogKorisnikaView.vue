<template>
  <div>
    <h2>Moje Police</h2>
    <button @click="logout">Logout</button>
    <table>
      <thead>
        <tr>
          <th>Naziv</th>
          <th>Primarna</th>
          <th>Stavke Police</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="polica in police" :key="polica.naziv">
          <td>{{ polica.naziv }}</td>
          <td>{{ polica.primarna }}</td>
          <td>
            <ul>
              <li v-for="stavka in polica.stavkaPolice" :key="stavka.recenzija.id">
                <div>
                  <strong>Recenzija:</strong>
                  <br />
                  Ocena: {{ stavka.recenzija.ocena }}
                  <br />
                  Tekst: {{ stavka.recenzija.tekst }}
                  <br />
                  Datum recenzije: {{ stavka.recenzija.datumRecenzije }}
                </div>
                <div>
                  <strong>Knjiga:</strong>
                  <br />
                  Naslov: {{ stavka.knjiga.naslov }}
                  <br />
                  Naslovna fotografija: {{ stavka.knjiga.naslovnaFotografija }}
                  <br />
                  Datum objavljivanja: {{ stavka.knjiga.datumObjavljivanja }}
                  <br />
                  Broj strana: {{ stavka.knjiga.brojStrana }}
                  <br />
                  Opis: {{ stavka.knjiga.opis }}
                  <br />
                  Ocena: {{ stavka.knjiga.ocena }}
                  <br />
                  Zanr: {{ stavka.knjiga.zanr.naziv }}
                </div>
              </li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      police: []
    };
  },
  mounted() {
    this.getPolicePrijavljenogKorisnika();
  },
  methods: {
    getPolicePrijavljenogKorisnika() {
      fetch('http://localhost:9090/api/police-prijavljenog-korisnika', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include' // Dodata opcija za slanje kredencijala
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to fetch data');
          }
        })
        .then(data => {
          this.police = data;
        })
        .catch(error => {
          console.error(error);
        });
    },

    logout() {
      fetch('http://localhost:9090/api/logout', {
        method: 'POST',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({ name: 'home' });
          } else {
            throw new Error('Failed to logout');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>
