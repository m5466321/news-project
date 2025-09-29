<script setup>
import { ref } from 'vue'
import {
    Edit,
    Delete,
    EditPen,
    More,
    CircleCheckFilled
} from '@element-plus/icons-vue'
const userInfo=ref([])
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(15)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    userList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    userList()
}

import { userListService } from '@/api/admin.js'
const userList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
    }
    let result = await userListService(params);
    total.value = result.data.total;
    userInfo.value = result.data.items;
    userInfo.value.forEach((item) => {
        item.status = item.status == 1 ? '正常' : '禁用'
        item.role = item.role == 1 ? '管理员' : '普通用户'
    })
}
userList()
import { userDeleteService } from '@/api/admin.js'
import { ElMessageBox } from 'element-plus'
import { ElMessage } from 'element-plus'


const deleteUser = (row) => {
    ElMessageBox.confirm(
        '您确认要删除该用户吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await userDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            userList()
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '取消删除',
            })
        })

}


import {changeRoleService} from '@/api/admin.js'
const dialogVisible = ref(false);
const changeRole=ref({
    role:'',
    id:''
})
const handleEdit=(row)=>{
    dialogVisible.value=true;
    changeRole.value.id=row.id;
}
const changeRoleServiceImpl=async()=>{
    let result=await changeRoleService(changeRole.value)
    ElMessage.success('修改成功'?result.message:'修改失败')
    dialogVisible.value=false;
    userList()
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>用户信息</strong></span>
            </div>
        </template>
        <el-table :data="userInfo" style="width: 100%">
            <el-table-column label="序号" width="100" prop="id"></el-table-column>
            <el-table-column label="账号" prop="username" width="100px"></el-table-column>
            <el-table-column label="昵称" prop="name" width="200px"> </el-table-column>
            <el-table-column label="邮箱" prop="email" width="250px"></el-table-column>
            <el-table-column label="联系方式" prop="phone" width="150px"></el-table-column>
            <el-table-column label="地址" prop="address" width="150px"></el-table-column>
            <el-table-column label="头像地址" prop="avatar" width="350px"></el-table-column>
            <el-table-column label="权限" prop="role" width="80px"></el-table-column>
            <el-table-column label="状态" prop="status" width="80px"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button  :icon="Edit" circle plain type="primary" @click="handleEdit(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteUser(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[15, 20, 25, 50]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
    </el-card>
    <el-dialog title="修改权限" v-model="dialogVisible" width="50%">
        <el-form :model="changeRole" label-width="80px">
            <el-form-item label="权限：">
                <el-select v-model="changeRole.role" placeholder="请选择">
                    <el-option label="管理员" value="1"></el-option>
                    <el-option label="普通用户" value="0"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="changeRoleServiceImpl">确定</el-button>
    </el-dialog>
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