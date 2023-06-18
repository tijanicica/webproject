<template>
  <div>
    <h2>Login</h2>
    <div>
      <input type="text" v-model="email" placeholder="Email" />
      <input type="password" v-model="password" placeholder="Password" />
      <button @click="login">Login</button>
    </div>
    <div v-if="loginError">
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
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Failed to login');
          }
        })
        .then(data => {
          localStorage.setItem('sessionToken', data.token);
          this.redirectToProfile();
        })
        .catch(error => {
          this.loginError = 'Failed to login. Please try again.';
          console.error(error);
        });
    },
    redirectToProfile() {
      // Get the logged-in user's role from the server
      fetch('http://localhost:9090/api/getUserRole', {
        method: 'GET',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            return response.text(); // Read the response as text
          } else {
            throw new Error('Failed to get user role');
          }
        })
        .then(data => {
          const role = data.trim(); // Remove leading/trailing whitespace from the response
          if (role === 'CITALAC') {
            this.$router.push({ name: 'police-prijavljenog-korisnika' });
          } else if (role === 'AUTOR') {
            this.$router.push({ name: 'autor-profil' });
          } else if (role === 'ADMINISTRATOR') {
            this.$router.push({ name: 'admin-profile' });
          } else {
            throw new Error('Unknown user role');
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>
