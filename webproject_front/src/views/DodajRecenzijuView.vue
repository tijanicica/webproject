<template>
    <div>
      <h2>Dodaj recenziju</h2>
      <form @submit.prevent="dodajRecenziju">
        <div>
          <label for="nazivKnjige">Naziv knjige:</label>
          <input type="text" id="nazivKnjige" v-model="nazivKnjige" required>
        </div>
        <div>
          <label for="police">Polica:</label>
          <select id="police" v-model="selectedPolice" multiple>
            <option v-for="polica in police" :key="polica.id" :value="polica.id">{{ polica.naziv }}</option>
          </select>
        </div>
        <div>
          <label for="datumiProcitano">Datum(i) kada je knjiga pročitana:</label>
          <input type="date" id="datumiProcitano" v-model="selectedDatumi" multiple>
        </div>
        <div>
          <label for="ocena">Ocena knjige:</label>
          <input type="number" id="ocena" v-model="ocena" min="1" max="10" required>
        </div>
        <div>
          <label for="tekst">Mišljenje o knjizi:</label>
          <textarea id="tekst" v-model="tekst" rows="4" cols="50" required></textarea>
        </div>
        <button type="submit">Dodaj recenziju</button>
      </form>
      <p v-if="obavestenje">{{ obavestenje }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        nazivKnjige: '',
        police: [],
        selectedPolice: [],
        selectedDatumi: [],
        ocena: null,
        tekst: '',
        obavestenje: ''
      };
    },
    created() {
      // Fetch available police from the server and populate the `police` array
      this.fetchPolice();
    },
    methods: {
      fetchPolice() {
        // Perform an API call to fetch the available police for the user
        // Replace the URL with the appropriate endpoint
        fetch('http://localhost:9090/api/police')
          .then(response => response.json())
          .then(data => {
            this.police = data; // Assign the fetched police data to the `police` array
          })
          .catch(error => {
            console.error('Error fetching police:', error);
          });
      },
      dodajRecenziju() {
        this.obavestenje = ''; // Reset the notification message
        const data = {
          ocena: this.ocena,
          tekst: this.tekst,
          datumRecenzije: this.selectedDatumi
        };
  
        const selectedPoliceIds = this.selectedPolice.map(polica => polica.id);
  
        fetch(`http://localhost:9090/api/dodaj-recenziju?nazivKnjige=${encodeURIComponent(this.nazivKnjige)}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data),
          credentials: 'include' // Include credentials (e.g., cookies) in the request
        })
          .then(response => {
            if (response.ok) {
              // Successful response from the server
              // Add the book to selected police
              return this.dodajKnjiguNaPolice(selectedPoliceIds);
            } else {
              // Unsuccessful response from the server
              throw new Error('Neuspešno dodavanje recenzije.');
            }
          })
          .then(() => {
            // Redirect the user to the appropriate page
            this.$router.push({ name: 'police-prijavljenog-korisnika' });
          })
          .catch(error => {
            this.obavestenje = error.message;
          });
      },
      dodajKnjiguNaPolice(policeIds) {
        const promises = policeIds.map(policaId => {
          return fetch(`http://localhost:9090/api/dodaj-knjigu-na-policu?policaId=${encodeURIComponent(policaId)}&nazivKnjige=${encodeURIComponent(this.nazivKnjige)}`, {
            method: 'POST',
            credentials: 'include' // Include credentials (e.g., cookies) in the request
          });
        });
  
        return Promise.all(promises);
      }
    }
  };
  </script>
  