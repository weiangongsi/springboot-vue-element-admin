import request from '@/utils/request'

const baseModel = '/menu'

// 获取所有
export function getAllMenu() {
  return request({
    url: baseModel,
    method: 'get'
  })
}
