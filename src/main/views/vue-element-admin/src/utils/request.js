import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (res.access_token || res.success) {
      return res
    } else {
      Message({
        message: res.message || '操作失败',
        type: 'error',
        duration: 5 * 1000,
        showClose: true
      })
      return Promise.reject(res.message || 'error')
    }
  },
  error => {
    if (error.response.status === 401) {
      // to re-login
      MessageBox.confirm('您已退出，可以点击取消停留在此页面，或者重新登录', '确认退出', {
        confirmButtonText: '重新登陆',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    } else {
      const url = error.response.config.url
      // 登陆和验证码不显示弹窗
      if (url.indexOf('/oauth/token') === -1 && url.indexOf('/verify-code') === -1) {
        // 遇到弹窗操作防止页面抖动
        setTimeout(_ => {
          Message({
            message: error.response.data.message || '操作失败',
            type: 'error',
            duration: 5 * 1000
          })
        }, 100)
      }
    }
    // 返回给调用方
    return Promise.reject(error)
  }
)

export default service
