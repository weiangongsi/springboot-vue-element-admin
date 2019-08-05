import request from '@/utils/request'

const baseModel = '/role'

// 获取所有角色
export function getAllRole() {
  return request({
    url: baseModel,
    method: 'get'
  })
}

// 创建角色
export function createRole(data) {
  return request({
    url: baseModel,
    method: 'post',
    data
  })
}

// 创建角色
export function deleteRole(id) {
  return request({
    url: baseModel + `/${id}`,
    method: 'delete'
  })
}

// 创建角色
export function updateRole(data) {
  return request({
    url: baseModel,
    method: 'put',
    data
  })
}
