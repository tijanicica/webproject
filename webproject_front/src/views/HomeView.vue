<template>
  <div class="page-container">
    <h1 class="site-title">GOODREADS</h1>
    <div class="container">
      <table class="button-table">
        <tr>
          <td>
            <button class="btn" @click="pretraziKnjigu">Pretrazi knjigu</button>
          </td>
          <td>
            <button class="btn" @click="pregledajSveKnjige">Pregledaj sve knjige</button>
          </td>
          <td>
            <button class="btn" @click="pregledajSveRecenzije">Pregledaj sve recenzije</button>
          </td>
          <td>
            <button class="btn" @click="pregledajSveZanrove">Pregledaj sve zanrove</button>
          </td>
          <td>
            <button class="btn" @click="pregledajProfileKorisnika">Pregledaj profile korisnika</button>
          </td>
        </tr>
      </table>
      <div class="login-register-container">
        <a type="button" href="/login" class="btn">Login</a>
        <a type="button" href="/register" class="btn">Register</a>
        <a type="button" href="/podnesi-zahtev" class="btn">Podnesi zahtev</a>
      </div>
    </div>
  </div>
</template>

<style>
.page-container {
  text-align: center;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.button-table {
  margin-bottom: 20px;
}

.btn {
  margin-right: 10px;
}

.login-register-container {
  margin-top: 20px;
}

.site-title {
  font-family: fantasy;
}
</style>

<script>
export default {
  name: 'HomeView',
  methods: {
    pregledajSveKnjige() {
    window.location.href = "/knjige";
  },
  pregledajSveZanrove() {
    window.location.href = "/zanrovi";
  },
  pregledajSveRecenzije() {
    window.location.href = "/recenzije";
  },
  pregledajProfileKorisnika() {
    window.location.href = "/profili-korisnika";
  },
  pretraziKnjigu() {
    window.location.href = "/pretraga/knjiga/naslov/{naslov}";
  },
  }
};
</script>
