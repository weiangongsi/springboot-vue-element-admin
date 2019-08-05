import request from '@/utils/request'
const model = 'web'

export function findAll() {
  return request({
    url: model,
    method: 'get'
  })
}
