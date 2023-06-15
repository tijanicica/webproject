import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import GetAllKnjigeView from '../views/GetAllKnjigeView.vue'
import GetAllZanroviView from '../views/GetAllZanroviView.vue'
import GetAllRecenzijeView from '../views/GetAllRecenzijeView.vue'
import GetAllProfiliKorisnikaView from '../views/GetAllProfiliKorisnikaView.vue'
import GetKnjigaByNaslovView from '../views/GetKnjigaByNaslovView.vue'


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
    component: HomeView
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

  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
