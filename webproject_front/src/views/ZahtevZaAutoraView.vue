<template>
    <div class="page-container">
      <h1 class="site-title">GOODREADS</h1>
      <form @submit.prevent="podnesiZahtev">
        <div class="form-group">
          <label for="email">Email adresa:</label>
          <input type="email" id="email" v-model="zahtev.email" required />
        </div>
        <div class="form-group">
          <label for="telefon">Telefon:</label>
          <input type="text" id="telefon" v-model="zahtev.telefon" required />
        </div>
        <div class="form-group">
          <label for="poruka">Poruka:</label>
          <textarea id="poruka" v-model="zahtev.poruka" required></textarea>
        </div>
        <div class="form-group">
          <button type="submit" class="btn">Podnesi zahtev</button>
        </div>
        <div v-if="message" class="message">{{ message }}</div>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'ZahtevZaAutoraView',
    data() {
      return {
        zahtev: {
          email: '',
          telefon: '',
          poruka: ''
        },
        message: ''
      };
    },
    methods: {
      podnesiZahtev() {
        fetch('http://localhost:9090/api/zahtev-za-autora', {
          method: 'POST',
          //credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.zahtev)
        })
          .then(response => {
            if (response.ok) {
              this.message = 'Zahtev uspešno podnet.';
              this.$router.push({ name: 'home' });
            } else {
              this.message = 'Greška pri podnošenju zahteva.';
            }
          })
          .catch(error => {
            console.error(error);
            this.message = 'Greška pri podnošenju zahteva.';
          });
      }
    }
  };
  </script>

  
  