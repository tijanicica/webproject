<template>
    <div>
      <form @submit.prevent="dodajNovuPolicu">
        <label for="imePolice">Ime nove police:</label>
        <input type="text" id="imePolice" v-model="imePolice" required>
        <button type="submit">Dodaj</button>
      </form>
      <p v-if="policaPostoji" class="error-message">Polica sa datim imenom već postoji.</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        imePolice: '',
        policaPostoji: false
      };
    },
    methods: {
      dodajNovuPolicu() {
        fetch('http://localhost:9090/api/dodaj-novu-policu', {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: new URLSearchParams({
            imePolice: this.imePolice
          })
        })
        .then(response => {
          if (response.ok) {
            this.$router.push({ name: 'police-prijavljenog-korisnika' });
          } else {
            throw new Error('Neuspeno dodavanje nove police!');
          }
        })
        .catch(error => {
          console.error(error);
          this.policaPostoji = true;
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
  