<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Lock, Message, User, Location, Phone, EditPen } from '@element-plus/icons-vue'
const isRegister = ref(false)
const loginFormRef = ref(null)
const registerFormRef = ref(null)

const loginForm = ref({
  username: '',
  password: ''
})



const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 10, message: '长度在2到10个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在6到16个字符', trigger: 'blur' }
  ],
  repassword: [
    { required: true, message: '再次输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在6到16个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 1, max: 5, message: '长度在1到5个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
  ],
  phone: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
    { min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
  ],
}

import { userLoginService } from '../api/login.js'
import { useTokenStore } from '@/stores/token.js'
import useUserInfoStore from '@/stores/userInfo';
import { useRouter } from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const userStore = useUserInfoStore();
const login = async () => {
  let result = await userLoginService(loginForm.value);
  ElMessage.success('登录成功' ? '登录成功' : result.data.message);
  userStore.setInfo(result.data.userInfo);
  tokenStore.setToken(result.data.token);
  router.push('/home')
}
const rePassword = ref({
  repassword: '',
})
const registerForm = ref({
  username: '',
  password: '',
  name: '',
  email: '',
  phone: '',
  address: '',

})
import { userRegisterService } from '../api/login.js'

const handleRegister = async () => {
  debugger
  if (registerForm.value.password !== rePassword.value.repassword) {
    ElMessage.error('两次密码不一致');
  }else{
    let result = await userRegisterService(registerForm.value);
    ElMessage.success('注册成功' ? '注册成功' : result.data.message);
    isRegister.value = false;
  }

}
//i18n和element-plus翻译
// Element Plus 语言包
import { useI18n } from 'vue-i18n';
import { watch } from 'vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
import en from 'element-plus/dist/locale/en.mjs';
import it from 'element-plus/dist/locale/it.mjs';
import ja from 'element-plus/dist/locale/ja.mjs';
import ko from 'element-plus/dist/locale/ko.mjs';

const { locale: i18nLocale } = useI18n();

// 语言配置映射表
const langMap = {
  zhCn: { i18n: 'zh', element: zhCn },
  en: { i18n: 'en', element: en },
  it: { i18n: 'it', element: it },
  ja: { i18n: 'ja', element: ja },
  ko: { i18n: 'ko', element: ko },
};

// 响应式变量
const selectedLang = ref('zhCn');
const elementLocale = ref(langMap[selectedLang.value].element);

// 监听语言变化
watch(selectedLang, (newLang) => {
  const { i18n, element } = langMap[newLang];
  i18nLocale.value = i18n;      // 更新 i18n
  elementLocale.value = element; // 更新 Element Plus
});

// 选项列表保持不变
const options = [
  { value: 'zhCn', label: '中文' },
  { value: 'en', label: 'English' },
  { value: 'it', label: 'Italiano' },
  { value: 'ja', label: '日本語' },
  { value: 'ko', label: '한국어' },
];


</script>

<template>
  <el-config-provider :locale="elementLocale">
    <div class="container">
      <div class="form-container" :class="{ active: isRegister }">
        <!-- 登录表单 -->
        <transition name="fade" mode="out-in">
          <el-form v-if="!isRegister" key="login" class="form login-form" :model="loginForm" rules="rules"
            ref="loginFormRef">
            <h2 class="form-title">{{ $t('message.欢迎登录') }}</h2>

            <el-form-item prop="username">
              <el-input v-model="loginForm.username" :placeholder="$t('message.username')" :prefix-icon="User"
                clearable />
            </el-form-item>

            <el-form-item prop="password">
              <el-input v-model="loginForm.password" :placeholder="$t('message.password')" :prefix-icon="Lock"
                show-password />
            </el-form-item>

            <el-button type="primary" class="submit-btn" @click="login">
              {{ $t('message.立即登录') }}
            </el-button>
          </el-form>

          <!-- 注册表单 -->
          <el-form v-else key="register" class="form register-form" :model="registerForm"
          :rules="rules"
            ref="registerFormRef">
            <h2 class="form-title">{{ $t('message.用户注册') }}</h2>

            <el-form-item prop="username">
              <el-input v-model="registerForm.username" :placeholder="$t('message.username')" :prefix-icon="User"
                clearable />
            </el-form-item>

            <el-form-item prop="password">
              <el-input v-model="registerForm.password" :placeholder="$t('message.password')" :prefix-icon="Lock"
                show-password />
            </el-form-item>
            <el-form-item prop="rePassword">
              <el-input v-model="rePassword.repassword" :placeholder="$t('message.repassword')" :prefix-icon="Lock"
                show-password />
            </el-form-item>
            <el-form-item prop="name">
              <el-input v-model="registerForm.name" :placeholder="$t('message.name')" :prefix-icon="EditPen"
                clearable />
            </el-form-item>
            <el-form-item prop="email">
              <el-input v-model="registerForm.email" :placeholder="$t('message.email')" :prefix-icon="Message"
                clearable />
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="registerForm.phone" :placeholder="$t('message.phone')" :prefix-icon="Phone"
                clearable />
            </el-form-item>
            <el-form-item prop="address">
              <el-input v-model="registerForm.address" :placeholder="$t('message.address')" :prefix-icon="Location"
                clearable />
            </el-form-item>

            <el-button type="primary" class="submit-btn" @click="handleRegister">
              {{ $t('message.立即注册') }}
            </el-button>

          </el-form>
        </transition>
      </div>

      <div class="switch-container">
        <div class="switch-content">
          <h1>{{ $t('message.欢迎来到新闻浏览系统') }}</h1>
          <p>{{ $t('message.在这里') }}</p>
          <p>{{ $t('message.同时') }}</p>
          <p>{{ $t('message.让我们') }}</p>
          <h2>{{ isRegister ? $t('message.已有账号') : $t('message.新用户') }}</h2>
          <p>{{ isRegister ? $t('message.立即登录体验更多功能') : $t('message.注册账号开启全新旅程') }}</p>
          <el-button type="primary" plain @click="isRegister = !isRegister">
            {{ isRegister ? $t('message.去登陆') : $t('message.去注册') }}
          </el-button>

          <el-select v-model="selectedLang" placeholder="Select" size="large" style="width: 100px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </div>
      </div>
    </div>
  </el-config-provider>
</template>


<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
  padding: 20px;
}

.form-container {
  position: relative;
  width: 500px;
  max-width: 100%;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.6s ease-in-out;
}

.form-container.active {
  width: 600px;
}

.form {
  padding: 40px;
  transition: opacity 0.3s ease;
}

.form-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 28px;
}

.submit-btn {
  width: 100%;
  margin-top: 20px;
  height: 45px;
  font-size: 16px;
  letter-spacing: 2px;
  transition: all 0.3s;
}

.switch-container {
  position: absolute;
  left: 10px;
  top: 20px;
  width: 40%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
  border-radius: 20px;
}

.switch-content {
  position: absolute;
  left: 0;
  top: 1%;
  width: 40%;
  height: 100%;
  padding: 40px;
  background: linear-gradient(45deg, #4ecdc4, #45b7af);
  color: white;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
  border-radius: 20px;

}

.switch-container {
  position: absolute;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: all 0.6s ease-in-out;

}

.form-container.active .switch-container {
  transform: translateX(-100%);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

@media (max-width: 768px) {
  .container {
    padding: 10px;
  }

  .form-container {
    width: 100%;
  }

  .form {
    padding: 30px 20px;
  }

  .switch-container {
    display: none;
  }

  .form-container.active {
    width: 100%;
  }
}

.el-input {
  transition: all 0.3s ease;
}

.el-input:focus-within {
  transform: translateY(-2px);
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}
</style>