<script setup>
import {
    Edit,
    Delete,
    EditPen,
    More,
    CircleCheckFilled
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const keyword = ref('')
const type = ref('')
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数
const newsInfo = ref([])
import useUserInfoStore from '@/stores/userInfo';
const userInfoStore = useUserInfoStore();
const userInfo = ref({ ...userInfoStore.info })
const onSizeChange = (size) => {
    pageSize.value = size
    newsList()
}
const onCurrentChange = (num) => {
    pageNum.value = num
    newsList()
}
import { newsListService } from '@/api/admin.js'
const newsList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        keyword: keyword.value ? keyword.value : null,
        type: type.value ? type.value : null,
    }
    let result = await newsListService(params);

    total.value = result.data.total;
    newsInfo.value = result.data.items;
}
newsList();

import { newsDeleteService } from '@/api/admin.js'
import { ElMessageBox } from 'element-plus'
import { ElMessage } from 'element-plus'


const deleteNews = (row) => {
    ElMessageBox.confirm(
        '您确认要删除该新闻条目吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await newsDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            newsList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })

}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>所有爬取新闻信息</strong></span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="关键词：" style="width:12%">
                <el-input placeholder="请输入" v-model="keyword">
                </el-input>
            </el-form-item>
            <el-form-item label="排序: " style="width:12%">
                <el-select placeholder="请选择" v-model="type">
                    <el-option label="浏览量最多" value="浏览量最多"></el-option>
                    <el-option label="点赞最多" value="点赞最多"></el-option>
                    <el-option label="评论最多" value="评论最多"></el-option>
                    <el-option label="最新" value=""></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="newsList">搜索</el-button>
                <el-button
                    @click="keyword = '', type = '', newsList()">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="newsInfo" style="width:100%;place-content: center">
            <el-table-column label="序号" width="80" type="index"> </el-table-column>
            <el-table-column label="标题" prop="title" width="350px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="内容" prop="text" width="280px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="浏览量" prop="views" width="80px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="评论量" prop="comments" width="80px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="点赞量" prop="thumbsUp" width="80px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="原文地址" prop="detailUrl" width="200px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="图片地址" prop="image" width="200px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="发布时间" prop="createTime" width="180px" overflow="hidden" show-overflow-tooltip=true></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteNews(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[10, 20, 25, 50]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

    </el-card>

</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>