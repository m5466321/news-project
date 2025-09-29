import request from '@/utils/request'
export const newsHotListService = ()=>{
    return request.get('/News/getNewsHot')
}
// 导出一个名为 newsListService 的常量，它是一个函数，接收一个参数 params
export const newsListService=(params)=>{
    // 使用 request 对象的 get 方法发送一个 GET 请求到 '/News/getNewsAll' 路径
    // 请求的参数通过 {params: params} 的形式传递，其中 params 是函数的参数
    return request.get('/News/getNewsAll',{params:params})
 }

export const newsDetailService=(params)=>{
    return request.get('/News/getNewsInfo',{params:params})
}