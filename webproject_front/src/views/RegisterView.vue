<template>
    <div>
      <h2>Registration</h2>
      <div>
        <input type="text" v-model="firstName" placeholder="First Name" />
        <input type="text" v-model="lastName" placeholder="Last Name" />
        <input type="text" v-model="username" placeholder="Username" />
        <input type="email" v-model="email" placeholder="Email" />
        <input type="password" v-model="password" placeholder="Password" />
        <input type="password" v-model="confirmPassword" placeholder="Confirm Password" />
        <button @click="register">Register</button>
      </div>
      <div v-if="registrationError">
        <p>{{ registrationError }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        firstName: '',
        lastName: '',
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        registrationError: ''
      };
    },
    methods: {
      register() {
        if (this.password !== this.confirmPassword) {
          this.registrationError = 'Passwords do not match';
          return;
        }
  
        const registerDto = {
          ime: this.firstName,
          prezime: this.lastName,
          korisnickoIme: this.username,
          mejlAdresa: this.email,
          lozinka: this.password,
          ponovljenaLozinka: this.confirmPassword // Dodaj polje za ponovljenu lozinku
        };
  
        fetch('http://localhost:9090/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(registerDto),
          credentials: 'include'
        })
          .then(response => {
            if (response.ok) {
              return response.text();
            } else {
              throw new Error('Failed to register');
            }
          })
          .then(data => {
            alert(data);
            this.$router.push({ name: 'login' });
          })
          .catch(error => {
            this.registrationError = 'Failed to register. Please try again.';
            console.error(error);
          });
      }
    }
  };
  </script>
  