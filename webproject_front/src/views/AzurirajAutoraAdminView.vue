<template>
    <div>
      <h2>Ažuriraj nalog autora</h2>
      <form @submit.prevent="azurirajNalog">
        <div>
          <label for="mejlAdresa">Mejl adresa:</label>
          <input type="text" id="mejlAdresa" v-model="mejlAdresa" required>
        </div>
        <div>
          <label for="ime">Ime:</label>
          <input type="text" id="ime" v-model="ime" required>
        </div>
        <div>
          <label for="prezime">Prezime:</label>
          <input type="text" id="prezime" v-model="prezime" required>
        </div>
        <div>
          <label for="datumRodjenja">Datum rođenja:</label>
          <input type="date" id="datumRodjenja" v-model="datumRodjenja" required>
        </div>
        <div>
          <label for="profilnaSlika">Profilna slika:</label>
          <input type="text" id="profilnaSlika" v-model="profilnaSlika">
        </div>
        <div>
          <label for="opis">Opis:</label>
          <textarea id="opis" v-model="opis"></textarea>
        </div>
        <button type="submit">Ažuriraj nalog autora</button>
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        mejlAdresa: '',
        ime: '',
        prezime: '',
        datumRodjenja: '',
        profilnaSlika: '',
        opis: '',
        errorMessage: ''
      };
    },
    methods: {
      azurirajNalog() {
        const autor = {
          ime: this.ime,
          prezime: this.prezime,
          datumRodjenja: this.datumRodjenja,
          profilnaSlika: this.profilnaSlika,
          opis: this.opis
        };
  
        fetch(`http://localhost:9090/api/azuriraj-nalog-autora-admin?mejlAdresa=${this.mejlAdresa}`, {
          method: 'PUT',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(autor)
        })
          .then(response => {
            if (response.ok) {
              return response.text();
            } else if (response.status === 403) {
              throw new Error('Niste administrator!');
            } else if (response.status === 404) {
              throw new Error('Nalog autora sa datom mejl adresom nije pronađen.');
            } else {
              throw new Error('Neuspešno ažuriranje naloga autora.');
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
  