<template>
  <div>
    <h2>Dodaj recenziju</h2>
    <form @submit.prevent="dodajRecenziju">
      <label>Naziv knjige:</label>
      <input v-model="nazivKnjige" type="text" required>
      <br>
      <label>Ocena:</label>
      <input v-model="recenzija.ocena" type="number" required>
      <br>
      <label>Tekst recenzije:</label>
      <textarea v-model="recenzija.tekst" required></textarea>
      <br>
      <label>Datum recenzije:</label>
      <input v-model="recenzija.datumRecenzije" type="date" required>
      <br>
      <button type="submit">Dodaj recenziju</button>
    </form>
    <br>
    <button @click="preusmeriNaDruguStranicu">Dodaj knjigu na jos neku policu</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nazivKnjige: '',
      recenzija: {
        ocena: 0,
        tekst: '',
        datumRecenzije: ''
      }
    };
  },
  methods: {
    dodajRecenziju() {
      const recenzijaData = {
        recenzijaBezKorisnikaDto: this.recenzija,
      };

      const url = `http://localhost:9090/api/dodaj-recenziju?nazivKnjige=${encodeURIComponent(this.nazivKnjige)}`;

      fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(recenzijaData),
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            console.log('Uspesno dodata recenzija.');
            this.$router.push({ name: 'police-prijavljenog-korisnika' });
          } else {
            throw new Error('Niste citalac ili autor!');
          }
        })
        .catch(error => {
          console.error(error.message);
          // Handle error
        });
    },
    preusmeriNaDruguStranicu() {
      // Perform redirection to another URL
      // Example: Redirect to '/druga-stranica'
      this.$router.push({ name: 'dodaj-knjigu-na-policu' });
    }
  }
};
</script>
