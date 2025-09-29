import request from '@/utils/request'

export const userLoginService = (data) => {
    return request.post('/user/login',data);
}

export const newsListService = (params) => {
    return request.get('/News/list',{params:params});
}

export const newsDeleteService = (id) => {
    
    return request.delete('/News/deleteNews?id='+id)
}

export const operateLogListService = (params) => {
    return request.get('/operate/logList',{params:params});
}
export const commentsListService = (params) => {
    return request.get('/Comments/getCommentsNum',{params:params});
}

export const thumbsUpListService = (params) => {
    return request.get('/Comments/getThumbsUpNum',{params:params});
}

export const userListService = (params) => {
    return request.get('/user/getUserInfo',{params:params});
}
export const userDeleteService = (id) => {
    return request.delete('/user/deleteUser?id='+id);
}
export const countService = () => {
    return request.get('/NewsCrawler/count');
}
export const newsService = () => {
    return request.get('/News/getNewsHotC');
}
export const chartService = () => {
    return request.get('/News/getChart');
}
export const getNewsService = () => {
    return request.get('/NewsCrawler/crawlNews');
}
export const changeRoleService=(data)=>{
    return request.put("/user/changeRole",data);
}