<template>
  <div>
    <h1>Sve Knjige</h1>
    <table>
      <thead>
        <tr>
          <th>Naslov</th>
          <th>Naslovna fotografija</th>
          <th>Datum objavljivanja</th>
          <th>Broj strana</th>
          <th>Opis</th>
          <th>Ocena</th>
          <th>Zanr</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="knjiga in knjige" :key="knjiga.naslov">
          <td>{{ knjiga.naslov }}</td>
          <td>{{ knjiga.naslovnaFotografija }}</td>
          <td>{{ knjiga.datumObjavljivanja }}</td>
          <td>{{ knjiga.brojStrana }}</td>
          <td>{{ knjiga.opis }}</td>
          <td>{{ knjiga.ocena }}</td>
          <td>{{ knjiga.zanr.naziv }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "GetAllKnjigeView",
  data() {
    return {
      knjige: [],
    };
  },
  mounted() {
    this.fetchKnjige();
  },
  methods: {
    fetchKnjige() {
      fetch("http://localhost:9090/api/knjige")
        .then((response) => response.json())
        .then((data) => {
          this.knjige = data;
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
  },
};
</script>

<style>
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

tr:hover {
  background-color: #f5f5f5;
}
</style>
