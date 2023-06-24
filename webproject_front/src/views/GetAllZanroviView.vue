<template>
    <div>
      <h2>Popis žanrova</h2>
      <ul>
        <li v-for="zanr in zanrovi" :key="zanr.naziv">{{ zanr.naziv }}</li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        zanrovi: [],
      };
    },
    mounted() {
      this.getZanrovi();
    },
    methods: {
      getZanrovi() {
        fetch('http://localhost:9090/api/zanrovi')
          .then((response) => response.json())
          .then((data) => {
            this.zanrovi = data;
          })
          .catch((error) => {
            console.error('Došlo je do pogreške prilikom dohvata žanrova:', error);
          });
      },
    },
  };
  </script>
  
  <style scoped>
  /* Stilizacija može biti prilagođena prema vašim potrebama */
  h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin-bottom: 0.5rem;
  }
  </style>
  