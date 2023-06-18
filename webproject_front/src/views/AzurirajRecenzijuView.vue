<template>
    <div>
      <h2>Ažuriraj recenziju</h2>
      <form @submit="azurirajRecenziju">
        <label for="ocena">Ocena:</label>
        <input type="number" id="ocena" v-model="recenzija.ocena" required>
        
        <label for="tekst">Tekst:</label>
        <textarea id="tekst" v-model="recenzija.tekst" required></textarea>
        
        <label for="datum">Datum recenzije:</label>
        <input type="date" id="datum" v-model="recenzija.datumRecenzije" required>
        
        <button type="submit">Ažuriraj recenziju</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        recenzija: {
          ocena: 0,
          tekst: '',
          datumRecenzije: ''
        }
      };
    },
    methods: {
      azurirajRecenziju() {
        const recenzijaId = this.$route.params.id;
        const url = `http://localhost:9090/api/azuriraj-recenziju/${recenzijaId}`;
        
        fetch(url, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          credentials: 'include',
          body: JSON.stringify(this.recenzija)
        })
          .then(response => {
            if (response.ok) {
                this.$router.push({ name: 'police-prijavljenog-korisnika' });
            } else {
              throw new Error('Failed to update recenzija');
            }
          })
          .catch(error => {
            console.error(error);
          });
      }
    }
  };
  </script>
  