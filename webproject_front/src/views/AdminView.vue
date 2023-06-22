<template>
    <div class="page-container">
      <h1 class="site-title">GOODREADS</h1>
      <button @click="logout">Logout</button>
      <div class="container">
        <table class="button-table">
          <tr>
            <td>
              <button class="btn" @click="dodajAutora">Dodaj autora</button>
            </td>
            <td>
              <button class="btn" @click="dodajZanr">Dodaj zanr</button>
            </td>
            <td>
              <button class="btn" @click="dodajKnjigu">Dodaj knjigu</button>
            </td>
            <td>
              <button class="btn" @click="azurirajKnjigu">Azuriraj knjigu</button>
            </td>
            <td>
              <button class="btn" @click="obrisiKnjigu">Obrisi knjigu</button>
            </td>
            <td>
              <button class="btn" @click="azurirajProfilAutora">Azuriraj profil autora</button>
            </td>
          </tr>
        </table>
      </div>
  
      <div class="container">
        <h2>Zahtevi za aktivaciju naloga autora</h2>
        <table class="request-table">
          <thead>
            <tr>
              <th>Email</th>
              <th>Telefon</th>
              <th>Poruka</th>
              <th>Akcije</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="zahtev in zahtevi" :key="zahtev.email">
              <td>{{ zahtev.email }}</td>
              <td>{{ zahtev.telefon }}</td>
              <td>{{ zahtev.poruka }}</td>
              <td>
                <button class="btn accept" @click="prihvatiZahtev(zahtev)">Prihvati</button>
                <button class="btn reject" @click="odbijZahtev(zahtev)">Odbij</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>

<script>

export default {
    name: 'AdminView',
    data() {
      return {
        zahtevi: []
      };
    },
    mounted() {
    this.fetchZahtevi();
  },
    methods: {
      fetchZahtevi() {
      fetch('http://localhost:9090/api/zahtevi', {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            throw new Error('Greška pri dobavljanju zahteva');
          }
        })
        .then(data => {
          this.zahtevi = data;
        })
        .catch(error => {
          console.error('Greška pri dobavljanju zahteva:', error);
        });
    },

    logout() {
      fetch('http://localhost:9090/api/logout', {
        method: 'POST',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            this.$router.push({ name: 'home' });
          } else {
            throw new Error('Failed to logout');
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
  
  
    prihvatiZahtev(zahtev) {
      fetch(`http://localhost:9090/api/prihvati-zahtev?email=${zahtev.email}`, {
        method: 'POST',
        credentials: 'include'
      })
        .then(response => {
          if (response.ok) {
            console.log('Zahtev prihvaćen');
            this.fetchZahtevi(); // Reload the requests after accepting one
          } else {
            throw new Error('Greška pri prihvatanju zahteva:', response.statusText);
          }
        })
        .catch(error => {
          console.error('Greška pri prihvatanju zahteva:', error);
        });
    },
  
      odbijZahtev(zahtev) {
        fetch(`http://localhost:9090/api/odbij-zahtev?email=${zahtev.email}`, {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(zahtev)
        })
          .then(response => {
            if (response.ok) {
              console.log('Zahtev odbijen');
              this.fetchZahtevi();
            } else {
              console.error('Greška pri odbijanju zahteva:', response.statusText);
            }
          })
          .catch(error => {
            console.error('Greška pri odbijanju zahteva:', error);
          });
      },

  dodajZanr() {
    window.location.href = "/dodaj-zanr";
  },
  dodajKnjigu() {
    window.location.href = "/dodaj-knjigu-admin";
  },
  obrisiKnjigu() {
    window.location.href = "/obrisi-knjigu-admin";
  },
  azurirajKnjigu() {
    window.location.href = "/azuriraj-knjigu-admin";
  },
  azurirajProfilAutora() {
    window.location.href = "/azuriraj-autora-admin";
  },
  dodajAutora() {
    window.location.href = "/kreiraj-autora-admin";
  },
    
},

    

  };
</script>  