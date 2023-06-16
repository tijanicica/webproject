<template>
  <div>
    <h2>Login</h2>
    <div>
      <input type="text" v-model="email" placeholder="Email" />
      <input type="password" v-model="password" placeholder="Password" />
      <button @click="login">Login</button>
    </div>
    <div v-if="isLoggedIn">
      <router-link :to="{ name: 'police-prijavljenog-korisnika' }">Go to Profile</router-link>
    </div>
    <div v-else-if="loginError">
      <p>{{ loginError }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      isLoggedIn: false,
      loginError: ''
    };
  },
  methods: {
    login() {
      const loginDto = {
        mejlAdresa: this.email,
        lozinka: this.password
      };

      fetch('http://localhost:9090/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginDto),
        credentials: 'include' // Dodata opcija za slanje kredencijala
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to login');
          }
        })
        .then(data => {
          // Save the session token in local storage
          localStorage.setItem('sessionToken', data.token);

          this.isLoggedIn = true;
          this.$router.push({ name: 'police-prijavljenog-korisnika' });
        })
        .catch(error => {
          this.loginError = 'Failed to login. Please try again.';
          console.error(error);
        });
    }
  }
};
</script>
