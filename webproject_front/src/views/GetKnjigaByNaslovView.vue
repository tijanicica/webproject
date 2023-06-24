<template>
    <div>
      <h2>Pretraga knjige po naslovu</h2>
      <div>
        <input type="text" v-model="unosNaslova" placeholder="Unesite naslov knjige" />
        <button @click="pretrazi">Pretraži</button>
      </div>
      <div v-if="knjige.length > 0">
        <h3>Rezultati pretrage:</h3>
        <table>
          <thead>
            <tr>
              <th>Naslov</th>
              <th>Naslovna fotografija</th>
              <th>Datum objavljivanja</th>
              <th>Broj strana</th>
              <th>Opis</th>
              <th>Ocena</th>
              <th>Zanr</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="knjiga in knjige" :key="knjiga.naslov">
              <td>{{ knjiga.naslov }}</td>
              <td>{{ knjiga.naslovnaFotografija }}</td>
              <!-- <td><img :src="'src/assets/' + knjiga.naslovnaFotografija" alt="Naslovna fotografija" /></td> -->
              <td>{{ formatDate(knjiga.datumObjavljivanja) }}</td>
              <td>{{ knjiga.brojStrana }}</td>
              <td>{{ knjiga.opis }}</td>
              <td>{{ knjiga.ocena }}</td>
              <td>{{ knjiga.zanr.naziv }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else-if="pretragaIzvrsena">
        <p>Nema rezultata za uneseni naslov.</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        unosNaslova: '',
        knjige: [],
        pretragaIzvrsena: false,
      };
    },
    methods: {
      pretrazi() {
        console.log(this.unosNaslova)
        fetch(`http://localhost:9090/api/pretraga/knjiga/naslov/${encodeURIComponent(this.unosNaslova)}`)
          .then((response) => response.json())
          .then((data) => {
            this.knjige = data;
            this.pretragaIzvrsena = true;
          })
          .catch((error) => {
            console.error('Došlo je do pogreške prilikom pretrage knjige:', error);
            this.pretragaIzvrsena = true;
          });
      },
      formatDate(date) {
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Date(date).toLocaleDateString('hr-HR', options);
      },
    },
  };
  </script>
  
  <style scoped>
  /* Stilizacija može biti prilagođena prema potrebama */
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  
  input {
    margin-right: 1rem;
  }
  
  button {
    padding: 0.5rem 1rem;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th,
  td {
    padding: 0.5rem;
    border: 1px solid #ccc;
  }
  
  img {
    width: 100px;
    height: auto;
  }
  </style>
  