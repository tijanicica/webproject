<template>
    <div>
      <form @submit="submitForm">
        <label for="email">Mejl adresa:</label>
        <input type="email" id="email" v-model="email" required>
  
        <label for="password">Lozinka:</label>
        <input type="password" id="password" v-model="password" required>
  
        <button type="submit">Prijavi se</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        email: '',
        password: ''
      };
    },
    methods: {
      submitForm(event) {
        event.preventDefault();
        
        // Pozovi metodu za prijavu na serveru
        // Ovde bi trebalo da se napravi HTTP zahtev ka "/api/login" endpointu
        
        // Nakon uspešne prijave, možeš obaviti redirekciju na odgovarajuću stranicu
        // npr. this.$router.push('/dashboard');
      }
    }
  };
  </script>
  