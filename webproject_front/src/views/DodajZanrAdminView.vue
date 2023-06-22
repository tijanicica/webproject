<template>
    <div class="page-container">
      <h1 class="site-title">GOODREADS</h1>
      <div class="container">
        <form @submit.prevent="dodajZanr">
          <label for="naziv">Naziv žanra:</label>
          <input type="text" id="naziv" v-model="zanr.naziv" required>
          <button type="submit" class="btn">Dodaj žanr</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'DodajZanrView',
    data() {
      return {
        zanr: {
          naziv: ''
        }
      };
    },
    methods: {
      dodajZanr() {
        fetch('http://localhost:9090/api/dodaj-zanr', {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.zanr)
        })
          .then(response => {
            if (response.ok) {
            
              console.log('Žanr uspešno dodat');
              this.$router.push({ name: 'admin-profil' });
              // Handle success, e.g., show a success message
            } else {
              console.error('Greška pri dodavanju žanra:', response.statusText);
              // Handle error, e.g., show an error message
            }
          })
          .catch(error => {
            console.error('Greška pri dodavanju žanra:', error);
            // Handle error, e.g., show an error message
          });
      }
    }
  };
  </script>
  

  