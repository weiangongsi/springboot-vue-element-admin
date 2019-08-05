/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const onlineUserRouter = {
  path: '/online-users',
  component: Layout,
  children: [
    {
      path: 'index',
      component: () => import('@/views/system/online-users/index'),
      name: 'OnlineUsers',
      meta: { title: '在线用户', icon: 'people', roles: 'ROLE_ONLINE_USERS' }
    }
  ]
}
export default onlineUserRouter
