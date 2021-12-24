import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const asyncRoutes = [
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: '控制台', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/serve',
    component: Layout,
    alwaysShow: true,
    meta: { title: '服务管理', icon: 'serve' },
    children: [
      {
        path: 'list',
        component: () => import('@/views/serve/list'),
        name: 'Serve',
        meta: { title: '我的服务' }
      },
      {
        path: 'subscribe',
        component: () => import('@/views/serve/subscribe'),
        name: 'subscribe',
        meta: { title: '我的订阅' }
      },
      {
        path: 'subscribeDetail',
        component: () => import('@/views/serve/subscribeDetail'),
        name: 'subscribeDetail',
        hidden: true,
        meta: { title: '服务详情' }
      },
      {
        path: 'add',
        component: () => import('@/views/serve/add'),
        hidden: true,
        name: 'add',
        meta: { title: '创建服务' }
      },
      {
        path: 'detail',
        component: () => import('@/views/serve/detail'),
        name: 'detail',
        hidden: true,
        meta: { title: '服务详情' }
      }
    ]
  },
  {
    path: '/api',
    component: Layout,
    alwaysShow: true,
    meta: { title: 'API管理', icon: 'api' },
    children: [
      {
        path: 'list',
        component: () => import('@/views/api/list'),
        name: 'App',
        meta: { title: 'API列表' }
      },
      {
        path: 'add',
        component: () => import('@/views/api/add'),
        name: 'release',
        hidden: true,
        meta: { title: '创建API' }
      },
      {
        path: 'upstream',
        component: () => import('@/views/upstream/index'),
        name: 'Upstream',
        meta: { title: '上游管理', affix: true }
      },
      {
        path: 'upstream/create',
        component: () => import('@/views/upstream/detail'),
        hidden: true,
        name: 'UpstreamCreate',
        meta: { title: '创建上游服务', affix: true }
      },
      {
        path: 'upstream/edit/:id',
        component: () => import('@/views/upstream/detail'),
        hidden: true,
        name: 'UpstreamEdit',
        meta: { title: '配置上游服务', affix: true }
      }
    ]
  },
  {
    path: '/openServe',
    component: Layout,
    children: [
      {
        path: 'index',
        component: () => import('@/views/openServe/index'),
        name: 'openServe',
        meta: { title: '开放服务', icon: 'openServe', affix: true }
      },
      {
        path: 'detail',
        component: () => import('@/views/openServe/detail'),
        name: 'openServeDetail',
        hidden: true,
        meta: { title: 'API门户', affix: true }
      }
    ]
  },
  // {
  //   path: '/openServe',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'dashboard',
  //       component: () => import('@/views/openServe/index'),
  //       name: 'Dashboard',
  //       meta: { title: '开放服务', icon: 'openServe', affix: true }
  //     },
  //     {
  //       path: 'detail',
  //       component: () => import('@/views/openServe/detail'),
  //       hidden: true,
  //       name: 'Dashboard',
  //       meta: { title: '服务详情', affix: true }
  //     },
  //     {
  //       path: 'apiList',
  //       component: () => import('@/views/openServe/apiList'),
  //       hidden: true,
  //       name: 'Dashboard',
  //       meta: { title: '服务api列表', affix: true }
  //     }
  //   ]
  // },
  {
    path: '/log',
    component: Layout,
    children: [
      {
        path: 'log',
        component: () => import('@/views/system/log/list'),
        name: 'Log',
        meta: { title: '审计日志', icon: 'log', affix: true }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    alwaysShow: true,
    meta: { title: '系统设置', icon: 'system' },
    children: [
      {
        path: 'user',
        component: () => import('@/views/system/user/list'),
        name: 'User',
        meta: { title: '用户管理', affix: true }
      },
      {
        path: 'role',
        component: () => import('@/views/system/role/list'),
        name: 'Role',
        meta: { title: '角色管理', affix: true }
      },
      {
        path: 'index',
        component: () => import('@/views/user/index'),
        name: 'user',
        meta: { title: '个人信息', affix: true }
      }
    ]
  },
  // {
  //   path: '/data',
  //   component: Layout,
  //   alwaysShow: true,
  //   meta: { title: '数据管理' },
  //   children: [
  //     {
  //       path: 'Statistics',
  //       component: () => import('@/views/router/list'),
  //       name: 'App',
  //       meta: { title: '数据统计' }
  //     }
  //   ]
  // },
  /** when your routing map is too long, you can split it into small modules **/

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: () => import('@/views/login/index'),
    redirect: '/login',
    hidden: true
  },
  {
    path: '/market',
    component: () => import('@/views/marketing/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  ...asyncRoutes
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
