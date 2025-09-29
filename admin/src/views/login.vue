<template>
    <div class="login-container">
        <div class="login-box">
            <h2 class="title">新闻浏览后台管理系统</h2>
            <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef" @keyup.enter="login">
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名" :prefix-icon="User" />
                </el-form-item>

                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" :prefix-icon="Lock"
                        show-password />
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" class="login-btn" @click="login" :loading="loading">
                        登录
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import useUserInfoStore from '@/stores/userInfo.js'
import { userLoginService } from '@/api/admin.js'
import { useTokenStore } from '@/stores/token.js'
const router = useRouter()
const userStore = useUserInfoStore()
const tokenStore = useTokenStore()
const loginForm = ref({
    username: '',
    password: ''
})
const loginFormRef = ref(null)
const loading = ref(false)
const loginRules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ]
})
const login = async () => {
    let result = await userLoginService(loginForm.value);
    if(result.data.userInfo.role=='0'){
        ElMessage.error('用户权限不足')
    }else{
        ElMessage.success('登录成功' ? '登录成功' : result.data.message);
        userStore.setInfo(result.data.userInfo);
        tokenStore.setToken(result.data.token);
        router.push('/home')
    }
}
</script>


<style scoped lang="scss">
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background-color: #2d3a4b;

    .login-box {
        width: 400px;
        padding: 40px;
        background-color: #fff;
        border-radius: 4px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

        .title {
            margin-bottom: 30px;
            text-align: center;
            color: #409eff;
        }

        .login-btn {
            width: 100%;
            margin-top: 10px;
        }
    }
}
</style>