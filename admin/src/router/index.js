import { createRouter, createWebHistory } from 'vue-router'
import login from '@/views/login.vue'
import Layoutvue from '@/views/Layout.vue'
import News from '@/views/news.vue'
import Operate from '@/views/operate.vue'
import Comments from '@/views/comments.vue'
import thumbsUp from '@/views/thumbsUp.vue'
import userInfo from '@/views/userInfo.vue'
import count from '@/views/count.vue'
const routes = [
  {
    path: '/',
    name: 'Login',
    component: login
  },
  {path:'/home',component:Layoutvue,redirect:'/news',
    children:
    [  
      {
        path:'/news',
        component:News,
      },
      {
        path:'/operate',
        component:Operate,
      },
      {
        path:'/comments',
        component:Comments,
      },
      {
        path:'/thumbsUp',
        component:thumbsUp,
      },
      {
        path:'/userInfo',
        component:userInfo,
      },
      {
        path:'/tongji',
        component:count,
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes:routes
})

// 路由守卫
router.beforeEach((to, from) => {
  const isAuthenticated = localStorage.getItem('token')
  if (to.meta.requiresAuth && !isAuthenticated) {
    return { name: 'Auth' }
  }
})

export default router