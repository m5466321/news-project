import { createRouter, createWebHistory } from 'vue-router'
import home from '@/view/HomeView.vue'
import detail from '@/view/NewsDetail.vue'
import login from '@/view/Login.vue'
import person from '@/view/Person.vue'
const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  },
  {
    path: '/home',
    name: 'Home',
    component: home,
  },
  {
    path: '/news/:id',
    name: 'NewsDetail',
    component: detail,
    props: true
  },
  {
    path: '/person',
    name: 'Person',
    component: person,
    props: true
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from) => {
  const isAuthenticated = localStorage.getItem('token')
  if (to.meta.requiresAuth && !isAuthenticated) {
    return { name: 'Auth' }
  }
})

export default router