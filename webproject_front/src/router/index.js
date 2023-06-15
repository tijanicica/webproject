import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import GetAllKnjigeView from '../views/GetAllKnjigeView.vue'

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
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
