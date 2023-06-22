<template>
    <div>
      <h2>Ažuriraj knjigu</h2>
      <form @submit.prevent="azurirajKnjigu">
        <div>
          <label for="nazivKnjige">Naziv knjige:</label>
          <input type="text" id="nazivKnjige" v-model="nazivKnjige" required>
        </div>
        <div>
          <label for="naslov">Naslov knjige:</label>
          <input type="text" id="naslov" v-model="naslov" required>
        </div>
        <div>
          <label for="naslovnaFotografija">Naslovna fotografija:</label>
          <input type="text" id="naslovnaFotografija" v-model="naslovnaFotografija">
        </div>
        <div>
          <label for="ISBN">ISBN:</label>
          <input type="text" id="ISBN" v-model="ISBN">
        </div>
        <div>
          <label for="datumObjavljivanja">Datum objavljivanja:</label>
          <input type="date" id="datumObjavljivanja" v-model="datumObjavljivanja">
        </div>
        <div>
          <label for="brojStrana">Broj strana:</label>
          <input type="number" id="brojStrana" v-model="brojStrana">
        </div>
        <div>
          <label for="opis">Opis:</label>
          <textarea id="opis" v-model="opis"></textarea>
        </div>
        <div>
          <label for="ocena">Ocena:</label>
          <input type="number" id="ocena" v-model="ocena">
        </div>
        <div>
          <label for="zanr">Žanr:</label>
          <input type="text" id="zanr" v-model="zanr">
        </div>
        <button type="submit">Ažuriraj knjigu</button>
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        nazivKnjige: '',
        naslov: '',
        naslovnaFotografija: '',
        ISBN: '',
        datumObjavljivanja: '',
        brojStrana: 0,
        opis: '',
        ocena: 0,
        zanr: '',
        errorMessage: ''
      };
    },
    methods: {
      azurirajKnjigu() {
        const knjiga = {
          naslov: this.naslov,
          naslovnaFotografija: this.naslovnaFotografija,
          ISBN: this.ISBN,
          datumObjavljivanja: this.datumObjavljivanja,
          brojStrana: this.brojStrana,
          opis: this.opis,
          ocena: this.ocena,
          zanr: {
            naziv: this.zanr
          }
        };
  
        fetch(`http://localhost:9090/api/azuriraj-knjigu-admin?naslovKnjige=${this.nazivKnjige}`, {
          method: 'PUT',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(knjiga)
        })
          .then(response => {
            if (response.ok) {
              return response.text();
            } else if (response.status === 403) {
              throw new Error('Niste administrator!');
            } else if (response.status === 404) {
              throw new Error('Knjiga nije pronađena.');
            } else {
              throw new Error('Neuspešno ažuriranje knjige.');
            }
          })
          .then(data => {
            console.log(data); // Handle successful response
            this.$router.push({ name: 'admin-profil' });
          })
          .catch(error => {
            console.error(error);
            this.errorMessage = error.message;
          });
      }
    }
  };
  </script>
  
  <style>
  .error-message {
    color: red;
    margin-top: 10px;
  }
  </style>
  