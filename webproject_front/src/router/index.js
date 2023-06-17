import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import GetAllKnjigeView from '../views/GetAllKnjigeView.vue'
import GetAllZanroviView from '../views/GetAllZanroviView.vue'
import GetAllRecenzijeView from '../views/GetAllRecenzijeView.vue'
import GetAllProfiliKorisnikaView from '../views/GetAllProfiliKorisnikaView.vue'
import GetKnjigaByNaslovView from '../views/GetKnjigaByNaslovView.vue'
import GetPolicePrijavljenogKorisnikaView from '../views/GetPolicePrijavljenogKorisnikaView.vue'
import RegisterView from '../views/RegisterView.vue'
import DodajNovuPolicuView from '../views/DodajNovuPolicuView.vue'
import DodajKnjiguNaPolicuView from '../views/DodajKnjiguNaPolicuView.vue'
import ObrisiKnjiguSaPoliceView from '../views/ObrisiKnjiguSaPoliceView.vue'
import AzurirajProfilView from '../views/AzurirajProfilView.vue'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/api/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/api/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/api/knjige',
    name: 'knjige',
    component: GetAllKnjigeView
  },
  {
    path: '/api/zanrovi',
    name: 'zanrovi',
    component: GetAllZanroviView
  },
  {
    path: '/api/recenzije',
    name: 'recenzije',
    component: GetAllRecenzijeView
  },
  {
    path: '/api/profili-korisnika',
    name: 'profili-korisnika',
    component: GetAllProfiliKorisnikaView
  },
  {
    path: '/api/pretraga/knjiga/naslov/:naslov',
    name: 'GetKnjigaByNaslov',
    component: GetKnjigaByNaslovView,
  },
  {
    path: '/api/police-prijavljenog-korisnika',
    name: 'police-prijavljenog-korisnika',
    component: GetPolicePrijavljenogKorisnikaView,
  },
  {
    path: '/api/dodaj-novu-policu',
    name: 'dodaj-novu-policu',
    component: DodajNovuPolicuView,
  },
  {
    path: '/api/dodaj-knjigu-na-policu',
    name: 'dodaj-knjigu-na-policu',
    component: DodajKnjiguNaPolicuView,
  },
  {
    path: '/api/obrisi-knjigu-sa-police',
    name: 'obrisi-knjigu-sa-police',
    component: ObrisiKnjiguSaPoliceView,
  },
  {
    path: '/api/azuriraj-profil',
    name: 'azuriraj-profil',
    component: AzurirajProfilView,
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
