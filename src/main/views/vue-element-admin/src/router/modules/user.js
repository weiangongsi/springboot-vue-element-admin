/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const webRouter = {
  path: '/user',
  component: Layout,
  children: [
    {
      path: 'index',
      component: () => import('@/views/system/user/index'),
      name: 'User',
      meta: { title: '用户管理', icon: 'peoples', roles: 'ROLE_USER' }
    }
  ]
}
export default webRouter
