<template>
    <div class="page-container">
      <h1 class="site-title">GOODREADS</h1>
      <div class="container">
        <form @submit.prevent="dodajKnjigu">
          <label for="naslov">Naslov:</label>
          <input type="text" id="naslov" v-model="knjiga.naslov" required>
  
          <label for="naslovnaFotografija">Naslovna fotografija:</label>
          <input type="text" id="naslovnaFotografija" v-model="knjiga.naslovnaFotografija" required>
  
          <label for="datumObjavljivanja">Datum objavljivanja:</label>
          <input type="date" id="datumObjavljivanja" v-model="knjiga.datumObjavljivanja" required>
  
          <label for="brojStrana">Broj strana:</label>
          <input type="number" id="brojStrana" v-model="knjiga.brojStrana" required>
  
          <label for="opis">Opis:</label>
          <textarea id="opis" v-model="knjiga.opis" required></textarea>
  
          <label for="ocena">Ocena:</label>
          <input type="number" id="ocena" v-model="knjiga.ocena" required>
  
          <label for="zanr">Žanr:</label>
          <input type="text" id="zanr" v-model="knjiga.zanr.naziv" required>
  
          <button type="submit" class="btn">Dodaj knjigu</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DodajKnjiguView',
    data() {
      return {
        knjiga: {
          naslov: '',
          naslovnaFotografija: '',
          datumObjavljivanja: '',
          brojStrana: '',
          opis: '',
          ocena: '',
          zanr: {
            naziv: ''
          }
        }
      };
    },
    methods: {
      dodajKnjigu() {
        fetch('http://localhost:9090/api/dodaj-knjigu', {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.knjiga)
        })
          .then(response => {
            if (response.ok) {
              console.log('Knjiga uspešno dodata');
              this.$router.push({ name: 'admin-profil' });
              // Handle success, e.g., show a success message
            } else {
              console.error('Greška pri dodavanju knjige:', response.statusText);
              // Handle error, e.g., show an error message
            }
          })
          .catch(error => {
            console.error('Greška pri dodavanju knjige:', error);
            // Handle error, e.g., show an error message
          });
      }
    }
  };
  </script>
  
  <style>
  /* CSS styles */
  </style>
  