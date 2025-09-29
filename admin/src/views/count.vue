<script setup>
import {onMounted,  ref} from 'vue'
import {countService} from '@/api/admin.js'
import {Filter,Document,View,User,Star,CollectionTag } from '@element-plus/icons-vue'

const countInfo = ref({})
const getCountInfo = async () => {
    let result = await countService()
    countInfo.value = result.data
}
getCountInfo();
import {newsService} from '@/api/admin.js'
const newsInfo = ref([])
const getNewsInfo = async () => {
    let result = await newsService()
    newsInfo.value = result.data
}
getNewsInfo();

import * as echarts from 'echarts';
// 折线图数据
const chartData = ref({
    dates: [], // 日期数据
    views: [], // 浏览量数据
    comments: [], // 评论量数据
    likes: [], // 点赞量数据
    news:[]// 文章数据
})

// 初始化图表
const initChart = () => {
    const chartDom = document.getElementById('main');
    const myChart = echarts.init(chartDom);
    const option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['浏览量', '点赞量', '评论量', '新闻量']
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: chartData.value.dates
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '浏览量',
                type: 'line',
                data: chartData.value.views
            },
            {
                name: '点赞量',
                type: 'line',
                data: chartData.value.likes
            },
            {
                name: '评论量',
                type: 'line',
                data: chartData.value.comments
            },
            {
                name: '新闻量',
                type: 'line',
                data: chartData.value.news
            }
        ]
    };
    myChart.setOption(option);
}
const chart= ref([])
import { chartService } from '@/api/admin.js'
const getChartData = async () => {
    let result = await chartService()
    chart.value = result.data
    // 模拟数据
    console.log(chart.value)
    for (let i = 0; i < chart.value.length; i++) {
        chartData.value.dates.push(chart.value[i].data)
        chartData.value.views.push(chart.value[i].views)
        chartData.value.comments.push(chart.value[i].comments)
        chartData.value.likes.push(chart.value[i].thumbsUp)
        chartData.value.news.push(chart.value[i].news)
    }

}

onMounted(() => {
    getCountInfo();
    getNewsInfo();
    getChartData().then(() => {
        initChart();
    });
});
import th1Image from "../assets/th1.jpg";
import th2Image from "../assets/th2.jpg";
import th3Image from "../assets/th3.jpg";
const zoumadeng=ref([
                {url:th1Image},
                {url:th2Image},
                {url:th3Image},
            ])

</script>
<template>
    <el-card class="page-container">
        <div class="block text-center">
            <span class="demonstration">
                欢迎浏览系统管理系统统计信息
            </span>
            <el-carousel height="500px">
                <el-carousel-item v-for="item in zoumadeng" :key="item">
                    <img :src="item.url" style="background-size: contain;"/>
                </el-carousel-item>
            </el-carousel>
        </div>
        <el-row>
            <el-col :span="6">
                <el-statistic title="新闻总量" :value="countInfo.newsNum">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <Document />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日浏览量" :value="countInfo.viewsNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <View />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日新增新闻量" :value="countInfo.newsNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <Filter />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日用户登录量" :value="countInfo.userLoginNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <User />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日新增评论量" :value="countInfo.commentsNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <View />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日新增点赞量" :value="countInfo.thumbsUpNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <Star />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
            <el-col :span="6">
                <el-statistic title="当日新增日志量" :value="countInfo.logNumDay">
                    <template #suffix>
                        <el-icon style="vertical-align: -0.125em">
                            <CollectionTag />
                        </el-icon>
                    </template>
                </el-statistic>
            </el-col>
        </el-row class="tag">
        <div>
            <el-row :gutter="50">
            <el-col :span="25">
                <h3>最热门新闻条目</h3>
                <el-table :data="newsInfo" style="width: 100%">
                    <el-table-column prop="title" label="标题" width="290px" />
                    <el-table-column prop="views" label="浏览量" width="80px" />
                </el-table>
            </el-col>
            <el-col :span="15">
                <h3>统计图表</h3>
                <div id="main" style="width: 100%; height: 520px;">

                </div>
            </el-col>
        </el-row>
        </div>

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
.demonstration {
  color:  #1e83c7;
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

</style>