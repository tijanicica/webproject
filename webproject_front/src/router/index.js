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
import DodajRecenzijuView from '../views/DodajRecenzijuView.vue'
import AutorView from '../views/AutorView.vue'
import AutorDodajNovuPolicuView from '../views/AutorDodajNovuPolicuView.vue'
import AutorDodajKnjiguNaPolicuView from '../views/AutorDodajKnjiguNaPolicuView.vue'
import AutorObrisiKnjiguSaPoliceView from '../views/AutorObrisiKnjiguSaPoliceView.vue'
import AutorAzurirajProfilView from '../views/AutorAzurirajProfilView.vue'
import AutorDodajKnjiguUSpisakKnjigaView from '../views/AutorDodajKnjiguUSpisakKnjigaView.vue'
import AutorAzurirajKnjiguView from '../views/AutorAzurirajKnjiguView.vue'
import AzurirajRecenzijuView from '../views/AzurirajRecenzijuView.vue'
import AdminView from '../views/AdminView.vue'
import DodajZanrAdminView from '../views/DodajZanrAdminView.vue'
import DodajKnjiguAdminView from '../views/DodajKnjiguAdminView.vue'
import ObrisiKnjiguAdminView from '../views/ObrisiKnjiguAdminView.vue'
import AzurirajKnjiguAdminView from '../views/AzurirajKnjiguAdminView.vue'
import AzurirajAutoraAdminView from '../views/AzurirajAutoraAdminView.vue'
import KreirajAutoraAdminView from '../views/KreirajAutoraAdminView.vue'
import ZahtevZaAutoraView from '../views/ZahtevZaAutoraView.vue'


const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/knjige',
    name: 'knjige',
    component: GetAllKnjigeView
  },
  {
    path: '/zanrovi',
    name: 'zanrovi',
    component: GetAllZanroviView
  },
  {
    path: '/recenzije',
    name: 'recenzije',
    component: GetAllRecenzijeView
  },
  {
    path: '/profili-korisnika',
    name: 'profili-korisnika',
    component: GetAllProfiliKorisnikaView
  },
  {
    path: '/pretraga/knjiga/naslov/:naslov',
    name: 'GetKnjigaByNaslov',
    component: GetKnjigaByNaslovView,
  },
  {
    path: '/police-prijavljenog-korisnika',
    name: 'police-prijavljenog-korisnika',
    component: GetPolicePrijavljenogKorisnikaView,
  },
  {
    path: '/dodaj-novu-policu',
    name: 'dodaj-novu-policu',
    component: DodajNovuPolicuView,
  },
  {
    path: '/dodaj-knjigu-na-policu',
    name: 'dodaj-knjigu-na-policu',
    component: DodajKnjiguNaPolicuView,
  },
  {
    path: '/obrisi-knjigu-sa-police',
    name: 'obrisi-knjigu-sa-police',
    component: ObrisiKnjiguSaPoliceView,
  },
  {
    path: '/azuriraj-profil',
    name: 'azuriraj-profil',
    component: AzurirajProfilView,
  },
  {
    path: '/dodaj-recenziju',
    name: 'dodaj-recenziju',
    component: DodajRecenzijuView,
  },
  {
    path: '/autor-profil',
    name: 'autor-profil',
    component: AutorView,
  },
  {
    path: '/autor-dodaj-novu-policu',
    name: 'autor-dodaj-novu-policu',
    component: AutorDodajNovuPolicuView,
  },
  {
    path: '/autor-dodaj-knjigu-na-policu',
    name: 'autor-dodaj-knjigu-na-policu',
    component: AutorDodajKnjiguNaPolicuView,
  },
  {
    path: '/autor-obrisi-knjigu-sa-police',
    name: 'autor-obrisi-knjigu-sa-police',
    component: AutorObrisiKnjiguSaPoliceView,
  },
  {
    path: '/autor-azuriraj-profil',
    name: 'autor-azuriraj-profil',
    component: AutorAzurirajProfilView,
  },
  {
    path: '/autor-dodaj-knjigu-u-spisak-knjiga',
    name: 'autor-dodaj-knjigu-u-spisak-knjiga',
    component: AutorDodajKnjiguUSpisakKnjigaView,
  },
  {
    path: '/autor-azuriraj-knjigu',
    name: 'autor-azuriraj-knjigu',
    component: AutorAzurirajKnjiguView,
  },
  {
    path: '/azuriraj-recenziju',
    name: 'azuriraj-recenziju',
    component: AzurirajRecenzijuView,
  },
  {
    path: '/admin-profil',
    name: 'admin-profil',
    component: AdminView,
  },
  {
    path: '/dodaj-zanr',
    name: 'dodaj-zanr',
    component: DodajZanrAdminView,
  },
  {
    path: '/dodaj-knjigu-admin',
    name: 'dodaj-knjigu-admin',
    component: DodajKnjiguAdminView,
  },
  {
    path: '/obrisi-knjigu-admin',
    name: 'obrisi-knjigu-admin',
    component: ObrisiKnjiguAdminView,
  },
  {
    path: '/azuriraj-knjigu-admin',
    name: 'azuriraj-knjigu-admin',
    component: AzurirajKnjiguAdminView,
  },
  {
    path: '/azuriraj-autora-admin',
    name: 'azuriraj-autora-admin',
    component: AzurirajAutoraAdminView,
  },
  {
    path: '/kreiraj-autora-admin',
    name: 'kreiraj-autora-admin',
    component: KreirajAutoraAdminView,
  },
  {
    path: '/podnesi-zahtev',
    name: 'podnesi-zahtev',
    component: ZahtevZaAutoraView,
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
