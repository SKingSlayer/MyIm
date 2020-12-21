import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Success from '@/components/Success'
import AppMain from "../components/AppMain";
import Chat from "../components/Chat";
import Chat1 from "../components/Chat1";

Vue.use(Router)

export default new Router({

  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: Success
    },
    {
      path: '/success',
      name: 'Success',
      component: Success,
      children: [{
        path: '/Success/AppMain',
        name: 'friends',
        component: AppMain,
        children: [{
            path: '/Success/AppMain/chat',
            name: 'friend',
            component: Chat
        },
          {
            path: '/Success/AppMain/chat1',
            name: 'friend',
            component: Chat1
          }]
      }]
    }
  ]
})
