<template>
  <div class="page-container">
    <h1 class="site-title">GOODREADS</h1>
    <form @submit.prevent="azurirajRecenziju">
      <div class="form-group">
        <label for="tekst">Tekst recenzije:</label>
        <input type="text" id="tekst" v-model="recenzija.tekst" required />
      </div>
      <div class="form-group">
        <label for="ocena">Ocena:</label>
        <input type="number" id="ocena" v-model="recenzija.ocena" required />
      </div>
      <div class="form-group">
        <label for="datumRecenzije">Datum recenzije:</label>
        <input type="date" id="datumRecenzije" v-model="recenzija.datumRecenzije" required />
      </div>
      <div class="form-group">
        <button type="submit" class="btn">Azuriraj recenziju</button>
      </div>
      <div v-if="message" class="message">{{ message }}</div>
    </form>
  </div>
</template>

<script>
export default {
  name: 'AzurirajRecenzijuView',
  data() {
    return {
      recenzija: {
        ocena: 0,
        tekst: '',
        datumRecenzije: ''
      },
      message: ''
    };
  },
  methods: {
    azurirajRecenziju() {


      fetch(`http://localhost:9090/api/azuriraj-recenziju?tekst=${encodeURIComponent(this.$route.query.tekst)}`, {
        method: 'PUT',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.recenzija)
      })
        .then(response => {
          if (response.ok) {
            this.message = 'Recenzija uspešno ažurirana.';
            setTimeout(() => {
              this.$router.push({ name: 'police-prijavljenog-korisnika' });
            }, 3000);
          } else {
            this.message = 'Greška pri ažuriranju recenzije.';
          }
        })
        .catch(error => {
          console.error(error);
          this.message = 'Greška pri ažuriranju recenzije.';
        });
    }
  }
};
</script>
