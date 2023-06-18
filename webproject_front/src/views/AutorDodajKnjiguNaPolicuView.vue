<template>
    <div>
      <h2>Dodaj knjigu na policu</h2>
      <form @submit.prevent="dodajKnjigu">
        <div>
          <label for="nazivPrimarnePolice">Naziv primarne police:</label>
          <input type="text" id="nazivPrimarnePolice" v-model="nazivPrimarnePolice" required>
        </div>
        <div>
          <label for="nazivKreiranePolice">Naziv kreirane police:</label>
          <input type="text" id="nazivKreiranePolice" v-model="nazivKreiranePolice">
        </div>
        <div>
          <label for="nazivKnjige">Naziv knjige:</label>
          <input type="text" id="nazivKnjige" v-model="nazivKnjige" required>
        </div>
        <button type="submit">Dodaj knjigu na policu</button>
      </form>
      <p v-if="obavestenje">{{ obavestenje }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        nazivPrimarnePolice: '',
        nazivKreiranePolice: '',
        nazivKnjige: '',
        obavestenje: ''
      };
    },
    methods: {
      dodajKnjigu() {
        this.obavestenje = ''; // Resetuj obaveštenje
        const data = {
          nazivPrimarnePolice: this.nazivPrimarnePolice,
          nazivKreiranePolice: this.nazivKreiranePolice,
          nazivKnjige: this.nazivKnjige
        };
  
        fetch(`http://localhost:9090/api/dodaj-knjigu-na-policu?nazivPrimarnePolice=${encodeURIComponent(this.nazivPrimarnePolice)}&nazivKreiranePolice=${encodeURIComponent(this.nazivKreiranePolice)}&nazivKnjige=${encodeURIComponent(this.nazivKnjige)}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data),
          credentials: 'include' // Uključuje slanje kredencijala (npr. kolačića) u zahtevu
        })
          .then(response => {
            if (response.ok) {
              // Uspešan odgovor od servera
              if (this.nazivPrimarnePolice === 'Read') {
                this.$router.push({ name: 'dodaj-recenziju' });
              } else {
                this.$router.push({ name: 'autor-profil' });
              }
            } else {
              // Neuspešan odgovor od servera
              throw new Error('Neuspešno dodavanje knjige na policu.');
            }
          })
          .catch(error => {
            this.obavestenje = error.message;
          });
      }
    }
  };
  </script>
  
  