import request from '@/utils/request'

// 导出一个名为 userLoginService 的常量，它是一个函数，用于处理用户登录请求
export const userLoginService = (data) => {
    // 调用 request 对象的 post 方法，发送一个 POST 请求到 '/user/login' 路径
    // 请求的路径是 '/user/login'，data 参数包含了登录所需的数据，如用户名和密码
    return request.post('/user/login',data);
}

// 导出一个名为 userRegisterService 的常量，它是一个函数
export const userRegisterService = (data) => {
    // 调用 request 对象的 post 方法，发送一个 POST 请求到 '/user/register' 路径
    // 请求的数据为传入的 data 参数
    return request.post('/user/register',data);
}