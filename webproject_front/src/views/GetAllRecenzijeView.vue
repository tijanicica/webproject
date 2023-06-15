<template>
    <div>
      <h2>Popis recenzija</h2>
      <table>
        <thead>
          <tr>
            <th>Ocena</th>
            <th>Tekst</th>
            <th>Datum recenzije</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="recenzija in recenzije" :key="recenzija.id">
            <td>{{ recenzija.ocena }}</td>
            <td>{{ recenzija.tekst }}</td>
            <td>{{ formatDate(recenzija.datumRecenzije) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        recenzije: [],
      };
    },
    mounted() {
      this.getRecenzije();
    },
    methods: {
      getRecenzije() {
        fetch('http://localhost:9090/api/recenzije')
          .then((response) => response.json())
          .then((data) => {
            this.recenzije = data;
          })
          .catch((error) => {
            console.error('Došlo je do pogreške prilikom dohvata recenzija:', error);
          });
      },
      formatDate(date) {
        // Formatiranje datuma prema željenom formatu (npr. DD.MM.GGGG.)
        // Prilagodite funkciju prema potrebama
        const options = { day: '2-digit', month: '2-digit', year: 'numeric' };
        return new Date(date).toLocaleDateString('hr-HR', options);
      },
    },
  };
  </script>
  
  <style scoped>

  h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  
  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th,
  td {
    padding: 0.5rem;
    text-align: left;
    border-bottom: 1px solid #ddd;
  }
  
  th {
    background-color: #f5f5f5;
  }
  </style>
  