/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const permissionRouter = {
  path: '/permission',
  component: Layout,
  children: [
    {
      path: 'index',
      component: () => import('@/views/system/permission/index'),
      name: 'Permission',
      meta: { title: '权限管理', icon: 'lock', roles: 'ROLE_PERMISSION' }
    }
  ]
}
export default permissionRouter
