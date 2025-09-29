<script setup>
import { ref } from 'vue'

const thumbsUpInfo=ref([])
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(15)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    thumbsUpList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    thumbsUpList()
}

import { thumbsUpListService } from '@/api/admin.js'
const thumbsUpList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
    }
    let result = await thumbsUpListService(params);
    total.value = result.data.total;
    thumbsUpInfo.value = result.data.items;
}
thumbsUpList()

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>点赞信息</strong></span>
            </div>
        </template>
        <el-table :data="thumbsUpInfo" style="width: 100%">
            <el-table-column label="序号" width="100" prop="id"></el-table-column>
            <el-table-column label="点赞人" prop="thumbsUser"></el-table-column>
            <el-table-column label="点赞新闻id" prop="newId"> </el-table-column>
            <el-table-column label="点赞人id" prop="userId"></el-table-column>
            <el-table-column label="点赞时间" prop="createTime" width="260px"></el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[15, 20, 25, 50]"
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