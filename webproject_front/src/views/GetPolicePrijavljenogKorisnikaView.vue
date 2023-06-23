<template>
  <div class="page-container">
    <h2>Moje Police</h2>
    <button class="logout-btn" @click="logout">Logout</button>

    <div class="button-container">
      <button class="btn" @click="dodajNovuPolicu">Dodaj novu policu</button>
      <button class="btn" @click="dodajKnjiguNaPolicu">Dodaj knjigu na policu</button>
      <button class="btn" @click="obrisiKnjiguSaPolice">Ukloni knjigu sa police</button>
      <button class="btn" @click="azurirajProfil">Ažuriraj profil</button>
      <button class="btn" @click="azurirajRecenziju">Ažuriraj recenziju</button>
    </div>

    <table class="police-table">
      <thead>
        <tr>
          <th>Naziv</th>
          <th>Primarna</th>
          <th>Stavke Police</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="polica in police" :key="polica.naziv">
          <td>{{ polica.naziv }}</td>
          <td>{{ polica.primarna }}</td>
          <td>
            <ul class="stavke-list">
              <li v-for="stavka in polica.stavkaPolice" :key="stavka.recenzija ? stavka.recenzija.id : null">
                <div class="recenzija-container" v-if="stavka.recenzija">
                  <strong>Recenzija:</strong>
                  <br />
                  Ocena: {{ stavka.recenzija.ocena }}
                  <br />
                  Tekst: {{ stavka.recenzija.tekst }}
                  <br />
                  Datum recenzije: {{ stavka.recenzija.datumRecenzije }}
                  <br />
                </div>
                <div class="knjiga-container">
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
                  Žanr: {{ stavka.knjiga.zanr.naziv }}
                </div>
              </li>
            </ul>
          </td>
          <td>
            <button class="delete-btn" v-if="!polica.primarna" @click="obrisiPolicu(polica.naziv)">Obriši policu</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style>
.page-container {
  text-align: center;
  padding: 40px 0;
}

.logout-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  background: none;
  border: none;
  font-size: 16px;
  color: #333;
  cursor: pointer;
}

.button-container {
  margin-bottom: 20px;
}

.btn {
  margin-right: 10px;
  padding: 10px 20px;
  background-color: #f0f0f0;
  color: #333;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  text-decoration: none;
  transition: background-color 0.2s ease;
}

.btn:hover {
  background-color: #e0e0e0;
}

.police-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.police-table th,
.police-table td {
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

.police-table th {
  font-weight: bold;
  text-align: left;
}

.stavke-list {
  padding: 0;
  list-style: none;
  margin: 0;
}

.recenzija-container {
  margin-bottom: 10px;
}

.knjiga-container {
  margin-bottom: 20px;
}

.delete-btn {
  padding: 6px 12px;
  background-color: #f0f0f0;
  color: #333;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.2s ease;
}

.delete-btn:hover {
  background-color: #e0e0e0;
}
</style>
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
        credentials: 'include'
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
    },

    dodajNovuPolicu() {
      window.location.href = "/dodaj-novu-policu";
    },
    dodajKnjiguNaPolicu() {
      window.location.href = "/dodaj-knjigu-na-policu";
    },
    obrisiKnjiguSaPolice() {
      this.$router.push({ name: 'obrisi-knjigu-sa-police' });
    },
    azurirajProfil() {
      this.$router.push({ name: 'azuriraj-profil' });
    },
    azurirajRecenziju() {
      this.$router.push({ name: 'azuriraj-recenziju' });
    },

    obrisiPolicu(naziv) {
      fetch(`http://localhost:9090/api/obrisi-policu-naziv?nazivPolice=${naziv}`, {
        method: 'DELETE',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            this.getPolicePrijavljenogKorisnika();
            //window.location.reload();
          } else {
            throw new Error('Failed to delete policu');
          }
        })
        .catch(error => {
          console.error(error);
        });
    },

  
  }
};
</script>
