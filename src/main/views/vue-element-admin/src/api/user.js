import request from '@/utils/request'
const qs = require('qs')
const baseModel = '/user'
// 登陆
export function login(data) {
  return request({
    url: '/oauth/token',
    method: 'post',
    data: qs.stringify({
      url: process.env.VUE_APP_BASE_API + '/oauth/token',
      grant_type: 'password',
      client_id: 'web-auto-update',
      client_secret: 'secret',
      username: data.username,
      password: data.password,
      scope: 'trust'
    })
  })
}

// 获取用户信息
export function getInfo(access_token) {
  return request({
    url: baseModel + '/info',
    method: 'get',
    params: { access_token }
  })
}

// 获取用户列表
export function getUsers(query) {
  return request({
    url: baseModel,
    method: 'get',
    params: query
  })
}

// 创建用户
export function createUser(data) {
  return request({
    url: baseModel,
    method: 'post',
    data
  })
}

// 更新
export function updateUser(data) {
  return request({
    url: baseModel,
    method: 'put',
    data
  })
}

// 更新
export function deleteUser(id) {
  return request({
    url: baseModel + `/${id}`,
    method: 'delete'
  })
}

// 校验用户名是否存在
export function validUsername(id, username) {
  return request({
    url: baseModel + '/valid-username',
    method: 'post',
    params: { id: id, username: username }
  })
}

// 退出
export function logout() {
  return request({
    url: baseModel + '/logout',
    method: 'post'
  })
}
