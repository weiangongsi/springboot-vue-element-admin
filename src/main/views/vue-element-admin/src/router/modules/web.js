/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const webRouter = {
  path: '/web',
  component: Layout,
  children: [
    {
      path: 'index',
      component: () => import('@/views/web/index'),
      name: 'Web',
      meta: { title: '网站管理', icon: 'international', roles: 'ROLE_WEB' }
    }
  ]
}
export default webRouter
