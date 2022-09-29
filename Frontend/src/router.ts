import Vue from 'vue';
import VueRouter, { RouteConfig } from "vue-router";
import Home from '@/components/front/pages/Home.vue';
import BackstageHome from '@/components/backstage/pages/BackstageHome.vue';
import Login from '@/components/backstage/pages/Login.vue';
import Register from '@/components/backstage/pages/Register.vue';
Vue.use(VueRouter);
const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/console',
    name: 'BackstageHome',
    component: BackstageHome
  },
  {
    path: '/console/login',
    component: Login
  },
  {
    path: '/console/register',
    component: Register
  },
  {
    path: '/console/profile',
    name: 'profile',
    // lazy-loaded
    component: () => import('./components/backstage/pages/Profile.vue')
  },
  {
    path: 'console/admin',
    name: 'admin',
    component: () => import('./components/backstage/pages/BoardAdmin.vue')
  },
  {
    path: 'console/hotel_admin',
    name: 'hoteladimn',
    component: () => import('./components/backstage/pages/BoardHotelAdmin.vue')
  },
  {
    path: 'console/user',
    name: 'user',
    component: () => import('./components/backstage/pages/BoardUser.vue')
  },
  { 
    path: '/:pathMatch(.*)*',
     name: 'NotFound',
    component: Home },

];

const router = new VueRouter({
  // mode: "history",
  base: process.env.BASE_URL,
  routes
});

// router.beforeEach((to, from, next) => {
//     const publicPages = ['/login', '/register', '/home'];
//     const authRequired = !publicPages.includes(to.path);
//     const loggedIn = localStorage.getItem('user');
//     // trying to access a restricted page + not logged in
//     // redirect to login page
//     if (authRequired && !loggedIn) {
//       next('/login');
//     } else {
//       next();
//     }
//   });

  export default router;