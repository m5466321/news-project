<script setup>
import { ref } from 'vue'
const operateBy = ref('')
const operateLogInfo=ref([])
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(15)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    operateLogList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    operateLogList()
}

import { operateLogListService } from '@/api/admin.js'
const operateLogList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        operateBy:operateBy.value?operateBy.value:null,
    }
    let result = await operateLogListService(params);
    total.value = result.data.total;
    operateLogInfo.value = result.data.items;
}
operateLogList()

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>操作日志</strong></span>
            </div>
        </template>
        <el-form inline>
            <el-form-item label="操作人：" style="width:12%">
                <el-input placeholder="请输入" v-model="operateBy">
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="operateLogList">搜索</el-button>
                <el-button @click="operateBy = '',operateLogList()">重置</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="operateLogInfo" style="width: 100%">
            <el-table-column label="序号" width="100" prop="id"></el-table-column>
            <el-table-column label="操作人" prop="operateBy"> </el-table-column>
            <el-table-column label="操作内容" prop="operateContent"></el-table-column>
            <el-table-column label="操作时间" prop="operateTime" width="260px"></el-table-column>
            <el-table-column label="操作异常" prop="operateException"></el-table-column>
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