<script setup>
import {
    List,
    UserFilled,
    User,
    SwitchButton,
    CaretBottom,
    Histogram,
    StarFilled,
    Comment,
    Grid
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'


import { ElMessage, ElMessageBox } from 'element-plus';
import {useTokenStore} from '@/stores/token.js'
const tokenStore=useTokenStore();
import { useRouter } from 'vue-router';
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore=useUserInfoStore();

const router = useRouter();
const handleCommand=(command)=>{
    if(command=='logout'){
        ElMessageBox.confirm(
        '您确认要退出吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async() => {
            
            tokenStore.removeToken();
            userInfoStore.removeInfo();
            router.push('/');
            ElMessage({
                type: 'success',
                message: '退出登录成功',
            })
        
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消退出登录',
            })
        })
    }else{
        router.push('/user/'+command)
    }
}
import {getNewsService} from '@/api/admin.js'
const getNews=()=>{
    getNewsService();
    ElMessage({
        type: 'success',
        message: '获取新闻信息成功',
    })
}
</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="transparent"  text-color="	#87CEFA"
                router>
                <el-menu-item index="/tongji">
                    <el-icon><Histogram /></el-icon>
                    <span>统计信息</span>
                </el-menu-item>
                <el-menu-item index="/news">
                    <el-icon><List /></el-icon>
                    <span>新闻信息</span>
                </el-menu-item>
                <el-menu-item index="/operate">
                    <el-icon><Grid /></el-icon>
                    <span>操作日志</span>
                </el-menu-item>
                <el-menu-item index="/comments">
                    <el-icon><Comment /></el-icon>
                    <span>评论信息</span>
                </el-menu-item>
                <el-menu-item index="/thumbsUp">
                    <el-icon><StarFilled /></el-icon>
                    <span>点赞信息</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/userInfo">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>用户资料</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div>新闻管理系统：<strong>{{userInfoStore.info.name}}</strong>
                <el-button @click="getNews">更新新闻</el-button>
                </div>
                <el-dropdown placement="bottom-end"@command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.avatar?userInfoStore.info.avatar:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
            <el-footer>

            </el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        // background-color: #232323;
        background-image: url('@/assets/980.jpg');
        &__logo {
            height: 120px;
            background: url('@/assets/logo2.png') no-repeat center / 190px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>