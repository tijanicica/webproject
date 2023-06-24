<template>
    <div>
      <h1>Obriši Knjigu</h1>
      <input type="text" v-model="naslovKnjige" placeholder="Unesite naslov knjige" />
      <button @click="obrisiKnjigu">Obriši Knjigu</button>
      <p>{{ message }}</p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        naslovKnjige: '',
        message: '',
      };
    },
    methods: {
      obrisiKnjigu() {
        const enkodiranNaslovKnjige = encodeURIComponent(this.naslovKnjige);
        fetch(`http://localhost:9090/api/obrisi-knjigu-admin?naslovKnjige=${enkodiranNaslovKnjige}`, {
          method: 'DELETE',
          credentials: 'include',
        })
          .then(response => {
            if (response.ok) {
              this.message = 'Knjiga je uspesno obrisana.';
              this.$router.push({ name: 'admin-profil' });
            } else if (response.status === 403) {
              this.message = 'Niste administrator!';
            } else {
              this.message = 'Došlo je do greške prilikom brisanja knjige.';
            }
          })
          .catch(error => {
            console.error('Greška prilikom zahteva:', error);
            this.message = 'Došlo je do greške prilikom brisanja knjige.';
          });
      },
    },
  };
  </script>
  
  <style scoped>
  /* Stilovi za komponentu */
  </style>
  