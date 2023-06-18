<template>
    <div>
      <h2>Obrisi knjigu sa police</h2>
      <div>
        <label for="nazivKnjige">Naziv knjige:</label>
        <input type="text" id="nazivKnjige" v-model="nazivKnjige" />
      </div>
      <div>
        <label for="nazivPolice">Naziv police:</label>
        <input type="text" id="nazivPolice" v-model="nazivPolice" />
      </div>
      <div>
        <button @click="ukloniKnjiguSaPolice">Ukloni knjigu sa police</button>
      </div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <div v-if="successMessage" class="success">{{ successMessage }}</div>
    </div>
  </template>
  
<script>
export default {
  data() {
    return {
      nazivKnjige: '',
      nazivPolice: '',
      errorMessage: '',
      successMessage: '',
    };
  },
  methods: {
    ukloniKnjiguSaPolice() {
      this.errorMessage = '';
      this.successMessage = '';

      // Make an API call to delete the book from the shelf using Fetch
      const url = 'http://localhost:9090/api/obrisi-knjigu-sa-police';
      const params = {
        nazivKnjige: this.nazivKnjige,
        nazivPolice: this.nazivPolice,
      };

      const queryString = Object.keys(params)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
        .join('&');

      fetch(`${url}?${queryString}`, {
        method: 'DELETE',
        credentials: 'include',
      })
        .then(response => {
          if (response.ok) {
            return response.text();
          } else {
            throw new Error('Request failed');
          }
        })
        .then(data => {
          this.successMessage = data;
          this.$router.push({ name: 'autor-profil' });
        })
        .catch(error => {
          this.errorMessage = error.message || 'An error occurred. Please try again later.';
        });
    },
  },
};
</script>
