<template>
    <div>
      <h2>Ažuriraj profil</h2>
      <div>
        <label for="ime">Ime:</label>
        <input type="text" id="ime" v-model="ime" />
      </div>
      <div>
        <label for="prezime">Prezime:</label>
        <input type="text" id="prezime" v-model="prezime" />
      </div>
      <div>
        <label for="datumRodjenja">Datum rođenja:</label>
        <input type="date" id="datumRodjenja" v-model="datumRodjenja" />
      </div>
      <div>
        <label for="profilnaSlika">Profilna slika:</label>
        <input type="text" id="profilnaSlika" v-model="profilnaSlika" />
      </div>
      <div>
        <label for="opis">Opis:</label>
        <textarea id="opis" v-model="opis"></textarea>
      </div>
      <div>
        <label for="mejlAdresa">Mejl adresa:</label>
        <input type="text" id="mejlAdresa" v-model="mejlAdresa" />
      </div>
      <div>
        <label for="novaLozinka">Nova lozinka:</label>
        <input type="password" id="novaLozinka" v-model="novaLozinka" />
      </div>
      <div>
        <label for="trenutnaLozinka">Trenutna lozinka:</label>
        <input type="password" id="trenutnaLozinka" v-model="trenutnaLozinka" />
      </div>
      <div>
        <button @click="azurirajProfil">Ažuriraj profil</button>
      </div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success">{{ successMessage }}</div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        ime: '',
        prezime: '',
        datumRodjenja: '',
        profilnaSlika: '',
        opis: '',
        mejlAdresa: '',
        novaLozinka: '',
        trenutnaLozinka: '',
        errorMessage: '',
        successMessage: '',
      };
    },
    methods: {
      azurirajProfil() {
        this.errorMessage = '';
        this.successMessage = '';
  
        // Make an API call to update the profile using Fetch
        const url = 'http://localhost:9090/api/azuriraj-profil';
  
        const params = {
          ime: this.ime,
          prezime: this.prezime,
          datumRodjenja: this.datumRodjenja,
          profilnaSlika: this.profilnaSlika,
          opis: this.opis,
          mejlAdresa: this.mejlAdresa,
          novaLozinka: this.novaLozinka,
          trenutnaLozinka: this.trenutnaLozinka,
        };
  
        const queryString = Object.keys(params)
          .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
          .join('&');
  
        fetch(`${url}?${queryString}`, {
          method: 'PUT',
          credentials: 'include',
        })
          .then(response => {
            if (response.ok) {
              return response.text();
            } else {
              throw new Error('Request failed');
            }
          })
          .then(data => {
            this.successMessage = data;
            this.$router.push({ name: 'autor-profil' });
          })
          .catch(error => {
            this.errorMessage = error.message || 'An error occurred. Please try again later.';
          });
      },
    },
  };
  </script>
  
  <style scoped>
  .error {
    color: red;
  }
  
  .success {
    color: green;
  }
  </style>
  