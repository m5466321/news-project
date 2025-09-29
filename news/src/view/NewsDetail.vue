<template>
  <el-config-provider :locale="elementLocale">
    <div class="container">
      <el-header class="header">
        <h3 class="header-title">{{ $t('message.title') }}
          <el-menu router class="header-menu" mode="horizontal" background-color="transparent"
            default-active="/news/:id" text-color="#000" active-text-color="#ffd04b" :ellipsis="false">
            <el-menu-item index="/home">{{ $t('message.home') }}</el-menu-item>
            <el-menu-item index="/news/:id">{{ $t('message.all') }}</el-menu-item>
            <el-menu-item index="/person">{{ $t('message.person') }}</el-menu-item>
            <el-menu-item index="/" @click="logout">{{ $t('message.loginOut') }}</el-menu-item>
            <el-menu-item><el-select v-model="selectedLang" placeholder="Select" size="large" style="width: 100px">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
          </el-select></el-menu-item>
            <el-menu-item index="/person" class="user-info" :style="{ marginLeft: 'auto', cursor: 'default' }">
              <div class="user-wrapper">
                <el-avatar :size="30" :src="userInfo.avatar" class="user-avatar"></el-avatar>
                <span class="user-name">{{ userInfo.name || userInfo.username }}</span>
              </div>
            </el-menu-item>
          </el-menu>
        </h3>
      </el-header>
      <el-main>
        <div class="detail-container animated fadeIn">
          <el-card>
            <h1>{{ newsInfo.title }}</h1>
            <div class="meta">
              <span>{{ $t('message.createTime') }}：{{ newsInfo.createTime }}</span>
              <!-- <el-tag type="danger">{{ news.category }}</el-tag> -->
            </div>
            <el-image :src="newsInfo.image" class="detail-cover" />

            <div class="content" v-html="newsDetail"></div>

            <div class="actions">
              <el-button type="danger" @click="handleLike">
                <el-icon>
                  <Star />
                </el-icon>{{ $t('message.thumbsUp') }} {{ newsInfo.thumbsUp }}
              </el-button>
            </div>

            <div class="comments">
              <h3>{{ $t('message.comment') }} ({{ newsInfo.comments }})</h3>
              <el-input v-model="comment.comments" placeholder="请输入评论..." class="comment-input">
                <template #append>
                  <el-button @click="addComment">{{ $t('message.share') }}</el-button>
                </template>
              </el-input>

              <transition-group name="comment-list">
                <div v-for="comment in commentList" :key="comment.id" class="comment-item">
                  <el-avatar :size="40" :src="comment.avatar" class="comment-avatar" />
                  <div class="comment-content">
                    <div class="user-name">{{ comment.createUser }}</div>
                    <span class="comment-time">{{ comment.createTime }}</span>
                    <div class="comment-text">{{ comment.comments }}</div>
                  </div>
                </div>
              </transition-group>
            </div>
          </el-card>
        </div>
      </el-main>
    </div>
  </el-config-provider>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
const route = useRoute()
const newsId = route.params.id
const newsInfo = ref([])
const newsDetail = ref()
import { newsDetailService } from '@/api/news.js'
const NewsDetail = async () => {
  let result = await newsDetailService({ id: newsId })
  newsInfo.value = result.data.news[0],
    newsDetail.value = result.data.data
}
NewsDetail()




// 点赞
import useUserInfoStore from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })
const thumbsUp = ref({
  newId: newsId,
  userId: userInfo.value.id,
  thumbsUser: userInfo.value.name
})
import { thumbsUpService } from '@/api/comments.js'
const handleLike = async () => {
  let result = await thumbsUpService(thumbsUp.value)
  ElMessage.success('点赞成功' ? '点赞成功' : result.data.message);
  NewsDetail()
}

// 评论列表

const commentList = ref([])
import { getCommentsService } from '@/api/comments.js'
const getComments = async () => {
  let result = await getCommentsService({ newId: newsId })
  commentList.value = result.data
  console.log(commentList.value)
}
getComments()

// 提交评论
const comment = ref({
  newId: newsId,
  userId: userInfo.value.id,
  comments: '',
  createUser: userInfo.value.name
})
import { addCommentsService } from '@/api/comments.js'
const addComment = async () => {
  let result = await addCommentsService(comment.value)
  ElMessage.success('评论成功' ? '评论成功' : result.data.message);
  NewsDetail(),
    getComments()
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

/* 容器样式 */
.comment-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

/* 评论项 */
.comment-item {
  display: flex;
  gap: 20px;
  padding: 20px;
  margin-bottom: 16px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.comment-item:hover {
  top: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

/* 头像样式 */
.comment-avatar {
  flex-shrink: 0;
  border: 2px solid #f0f0f0;
}

/* 内容区域 */
.comment-content {
  flex-grow: 1;
}

.user-info {
  display: flex;
  align-items: baseline;
  gap: 12px;
  margin-bottom: 8px;
}

.user-name {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
}

.comment-time {
  font-size: 12px;
  color: #95a5a6;
}

.comment-text {
  font-size: 14px;
  line-height: 1.6;
  color: #34495e;
  padding: 8px 0;
  white-space: pre-wrap;
}

/* 过渡动画 */
.comment-list-move,
.comment-list-enter-active {
  transition: all 0.5s ease;
}

.comment-list-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.comment-list-leave-active {
  transition: all 0.3s ease;
  position: absolute;
}

.comment-list-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

/* 新增样式 */
.user-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 0 10px;
}

.user-avatar {
  border: 2px solid #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.user-name {
  font-size: 14px;
  color: #333;
  max-width: 100px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 调整菜单项悬停效果 */
.user-info:hover {
  background-color: transparent !important;
}

/* 响应式处理 */
@media (max-width: 768px) {
  .user-name {
    display: none;
  }

  .user-wrapper {
    padding: 0;
  }
}

.user-menu-popper {
  min-width: 120px !important;

  .el-menu-item {
    font-size: 13px;
    padding: 0 12px;
    height: 36px;
    line-height: 36px;

    i {
      margin-right: 6px;
    }
  }
}

/* 头像悬停效果 */
.user-avatar {
  transition: transform 0.3s;

  &:hover {
    transform: scale(1.1);
  }
}

/* 添加分割线 */
.header-menu::before {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 1px;
  background: linear-gradient(to right,
      transparent 10%,
      rgba(0, 0, 0, 0.1) 50%,
      transparent 90%);
}
</style>