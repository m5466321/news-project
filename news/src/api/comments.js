import request from '@/utils/request'

export const thumbsUpService = (data) => {
    return request.post('/Comments/addThumbsUp',data)
}
export const addCommentsService = (data) => {
    return request.post('/Comments/addComments',data)
}
export const getCommentsService = (params) => {
    return request.get('/Comments/getComments',{params:params})
}