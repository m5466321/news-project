import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
//i18n
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

// 引入插件和语言包
import { createI18n } from 'vue-i18n'; 
import zh from '@/lang/zh'
import en from '@/lang/en'
import ko from '@/lang/hy'
import ja from '@/lang/ry'
import it from '@/lang/ydl'
const i18n = createI18n({  
  legacy: false,
  locale: 'zh',
    messages: {  
      en,  
      zh,
      ko,
      ja,
      it
    }  
  });  

const app = createApp(App)
const pinia = createPinia();
const persist = createPersistedState();
pinia.use(persist);
app.use(ElementPlus)
app.use(router)
app.use(pinia)
app.use(i18n);




app.mount('#app')
