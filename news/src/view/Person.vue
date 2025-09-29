<script setup>
import { ref,computed } from 'vue';
import { ElMessage } from 'element-plus';
import useUserInfoStore from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})

import { getUserService } from '@/api/user';
const getUserInfo = async() => {
    let params = {id:userInfoStore.info.id};
    let result = await getUserService(params);
    userInfoStore.setInfo(result.data[0]);
    userInfo.value =result.data[0];
}
getUserInfo()
const isEditing = ref(false);
;
import { updateUserService } from '@/api/user';
const form = ref({...userInfo.value});
const saveEdit = async() => {
    let params = {...form.value};
    let result = await updateUserService(params);
    isEditing.value = false;
    ElMessage.success('修改保存成功！'?'修改成功':result.data.message);
    getUserInfo();
}

// 取消修改
const cancelEdit = () => {
  form.value = {...userInfo.value};
  isEditing.value = false;
};
const avatarUrl=ref();

import { avatatUpdateService } from '@/api/user';
//头像上传处理
const handleAvatarSuccess = async () => {
  const avatarInfo=ref({
    avatar:avatarUrl.value,
    id:userInfoStore.info.id
  })
  let result = avatatUpdateService(avatarInfo.value);
  ElMessage.success('上传成功'?'上传成功':result.data.message);
  getUserInfo();
};

// 上传前验证
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG) {
    ElMessage.error('头像图片只能是 JPG/PNG 格式!');
  }
  if (!isLt2M) {
    ElMessage.error('头像图片大小不能超过 2MB!');
  }
  uploadAvatar(file);
  return isJPG && isLt2M;

}

import { avatarUploadService } from '@/api/user';
const uploadAvatar = async(file) => {
  let result = await avatarUploadService(file);
  avatarUrl.value = result.data;
  handleAvatarSuccess()
}

const logout = () => {
  // 清除用户信息
  userInfoStore.clear();
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
    <div class="container">
      <el-config-provider :locale="elementLocale">
        <el-header class="header">
            <h3 class="header-title">{{ $t('message.title') }}
                <el-menu router class="header-menu" mode="horizontal" background-color="transparent"
                    default-active="/person" text-color="#000" active-text-color="#ffd04b" :ellipsis="false">
                    <el-menu-item index="/home">{{ $t('message.home') }}</el-menu-item>
            <el-menu-item index="/news/:id">{{ $t('message.all') }}</el-menu-item>
            <el-menu-item index="/person">{{ $t('message.person') }}</el-menu-item>
            <el-menu-item index="/" @click="logout">{{ $t('message.loginOut') }}</el-menu-item>
                    <el-menu-item><el-select v-model="selectedLang" placeholder="Select" size="large" style="width: 100px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
          </el-select></el-menu-item>
                </el-menu>
            </h3>
        </el-header>
        <el-main>
            <div class="profile-container">
        <div class="profile-header">
          <h2>{{ $t('message.person') }}</h2>
          <h3>{{ $t('message.personInfo') }}</h3>
        </div>

        <div class="profile-body">
          <!-- 左侧头像上传 -->
          <div class="avatar-section">
            <el-upload
              class="avatar-uploader"
              
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="userInfo.avatar" :src=userInfo.avatar class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              <div class="upload-mask">
                <i class="el-icon-upload"></i>
                <div class="mask-text">{{ $t('message.changeAvatar') }}</div>
              </div>
              
            </el-upload>
          </div>

          <!-- 右侧信息表单 -->
          <div class="info-section">
            <el-form v-if="isEditing" :model="form" label-width="80px">
              <el-form-item :label="$t('message.username')">
                <el-input v-model="form.username" disabled/>
              </el-form-item>
              <el-form-item :label="$t('message.name')">
                <el-input v-model="form.name" />
              </el-form-item>
              <el-form-item :label="$t('message.email')">
                <el-input v-model="form.email" />
              </el-form-item>
              <el-form-item :label="$t('message.phone')">
                <el-input v-model="form.phone" />
              </el-form-item>
              <el-form-item :label="$t('message.address')">
                <el-input v-model="form.address" />
              </el-form-item>
              <div class="form-actions">
                <el-button type="primary" @click="saveEdit">{{ $t('message.save') }}</el-button>
                <el-button @click="cancelEdit">{{ $t('message.cancel') }}</el-button>
              </div>
            </el-form>

            <div v-else class="info-display">
              <div class="info-item">
                <label>{{ $t('message.username') }}：</label>
                <span>{{ userInfo.username }}</span>
              </div>
              <div class="info-item">
                <label>{{ $t('message.name') }}：</label>
                <span>{{ userInfo.name }}</span>
              </div>
              <div class="info-item">
                <label>{{ $t('message.email') }}：</label>
                <span>{{ userInfo.email }}</span>
              </div>
              <div class="info-item">
                <label>{{ $t('message.phone') }}：</label>
                <span>{{ userInfo.phone }}</span>
              </div>
              <div class="info-item">
                <label>{{ $t('message.address') }}：</label>
                <span>{{ userInfo.address}}</span>
              </div>
              <el-button type="primary" @click="isEditing = true">{{ $t('message.changeInfo') }}</el-button>
            </div>
          </div>
        </div>
      </div>
        </el-main>
      </el-config-provider>
    </div>
</template>
<style scoped>
.container {
    justify-content: center;
    align-items: center;
    min-height: 100vh;
    background: linear-gradient(45deg, #ff6b6b, #4ecdc4);
    padding: 20px;
}

.header {

    color: #fff;


}

.header h3 {
    left: 20px;
    font-size: 26px;
    top: 20px;
}
.profile-container {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 15px;
  padding: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.profile-header h2 {
  color: #2c3e50;
  border-bottom: 2px solid #4ecdc4;
  padding-bottom: 15px;
  margin-bottom: 30px;
}
.profile-header h3 {
  color: rgba(91, 196, 223, 0.7); /* 更淡的颜色 */
  border-bottom: 2px solid rgba(78, 205, 196, 0.7); /* 更淡的边框颜色 */
  padding-bottom: 10px;
  margin-bottom: 30px;
  font-size: 15px;
  font-style: italic; /* 字体倾斜 */
  opacity: 0.8; /* 透明度 */
}

.profile-body {
  display: flex;
  gap: 40px;
}

.avatar-section {
  flex: 0 0 200px;
}

.avatar-uploader {
  position: relative;
  width: 180px;
  height: 180px;
  object-fit: cover; /* 改为 contain 可以显示完整图片但可能有留白 */
  object-position: center center;
  border-radius: 50%;
  overflow: hidden;
  border: 3px solid #4ecdc4;
  cursor: pointer;
  transition: all 0.3s;
}

.avatar-uploader:hover {
  transform: scale(1.05);
}

.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.upload-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s;
}

.avatar-uploader:hover .upload-mask {
  opacity: 1;
}

.info-section {
  flex: 1;
}

.info-display .info-item {
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
}

.info-display label {
  color: #6c757d;
  margin-right: 10px;
}

.info-display span {
  color: #2c3e50;
  font-weight: 500;
}

.form-actions {
  margin-top: 30px;
  text-align: right;
}

.el-button {
  transition: all 0.3s;
}

.el-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(78, 205, 196, 0.3);
}
</style>