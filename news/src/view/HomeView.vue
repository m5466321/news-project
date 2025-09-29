<script setup>
import { ref,computed } from 'vue'
import { useRouter } from 'vue-router'
import { View, ChatDotRound, Star } from '@element-plus/icons-vue'
// 模拟数据
const hotNews = ref([
  { id: 1, title: '热门新闻1', views: 1234, comments: 56, likes: 89, cover: 'https://picsum.photos/400/200' },
  // 更多数据...
])

const latestNews = ref([
  { id: 2, title: '最新新闻1', time: '2023-07-20', category: '科技' },
  // 更多数据...
])

const router = useRouter()
const goToDetail = (id) => {
  router.push({ name: 'NewsDetail', params: { id } })
}

// 修改推荐新闻数据结构
import { newsHotListService } from "@/api/news";
const getNewsHot = async () => {
  let result = await newsHotListService();
  hotNews.value = result.data;
};
getNewsHot();


// 分页处理
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数
const onSizeChange = (size) => {
  pageSize.value = size
  newsAllList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  newsAllList()
}

import { newsListService } from "@/api/news";
const newsAllList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
  }
  let result = await newsListService(params);
  total.value = result.data.total;
  latestNews.value = result.data.items;
}
newsAllList();

import useUserInfoStore from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })

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
      <h3 class="header-title">{{ $t('message.title') }}</h3>
      <el-menu router class="header-menu" mode="horizontal" background-color="transparent" default-active="/home"
        text-color="#000" active-text-color="#ffd04b" :ellipsis="false">
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
    </el-header>

    <el-main>
      <div class="home-container">
        <el-row :gutter="20" class="animated fadeIn">
          <el-col :span="16">
            <h2 class="section-title">🔥 {{ $t('message.hotNews') }}</h2>
            <transition-group name="list">
              <el-card v-for="news in hotNews " :key="news.id" class="news-card hover-effect"
                @click="goToDetail(news.id)">
                <el-image :src="news.image" class="news-cover" fit="cover" />
                <h3>{{ news.title }}</h3>
                <div class="stats">
                  <span><el-icon>
                      <View />
                    </el-icon> {{ news.views }}</span>
                  <span><el-icon>
                      <ChatDotRound />
                    </el-icon> {{ news.comments }}</span>
                  <span><el-icon>
                      <Star />
                    </el-icon> {{ news.thumbsUp }}</span>
                </div>
              </el-card>
            </transition-group>
          </el-col>

          <el-col :span="8">
            <h2 class="section-title">📰 {{ $t('message.news') }}</h2>
            <transition-group name="list">
              <el-card v-for="news in latestNews" :key="news.id" class="news-card latest-news hover-effect"
                @click="goToDetail(news.id)">
                <h4>{{ news.title }}</h4>
                <div class="meta">
                  <span class="time">{{ news.text }}</span>
                  <el-tag type="info">{{ news.createTime }}</el-tag>
                </div>

              </el-card>
              <el-pagination class="pagination" background @size-change="onSizeChange" @current-change="onCurrentChange"
                :current-page="pageNum" :page-size="pageSize" :page-sizes="[10, 20, 30, 40]"
                layout="sizes,prev, pager, next,jumer" :total="total" size="small" />
            </transition-group>
          </el-col>
        </el-row>
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



.home-container {

  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;

}

.hover-effect {
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
}

.hover-effect:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.news-cover {
  width: 100%;
  height: 200px;
  border-radius: 4px;
  margin-bottom: 10px;
}

.stats {
  display: flex;
  gap: 15px;
  color: #666;
  margin-top: 10px;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

.pagination {
  margin-top: 20px;

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