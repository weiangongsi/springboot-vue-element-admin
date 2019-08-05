import request from '@/utils/request'

const baseModel = '/online-users'

/**
 * 所有在线用户
 */
export function getAllOnlineUsers() {
  return request({
    url: baseModel + '/all',
    method: 'get'
  })
}

/**
 * 强制退出用户
 * @param loginName 登录名
 */
export function forceLogout(loginName) {
  return request({
    url: baseModel + `/force-logout/${loginName}`,
    method: 'delete'
  })
}
