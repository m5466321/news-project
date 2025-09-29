import request from '@/utils/request'
export const updateUserService = (data) => {
    return request.post('/user/updateUserInfo', data)
}
export const getUserService = (params) => {
    return request.get('/user/userInfo',{params:params})
}
export const avatatUpdateService= (data) => {
    return request.post('/user/updateAvatar', data)
}

export const avatarUploadService =(file) => {
    
    const formData = new FormData();
    // 将文件添加到FormData对象中
    formData.append('file', file);
  
    // 使用axios发送POST请求
    return request.post('/file/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
}