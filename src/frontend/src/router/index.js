import Vue from 'vue'
//import App from 'App.vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import HelloWorld2 from '@/components/HelloWorld2'
import LoginPage from '@/pages/LoginPage'
import HomePage from '@/pages/HomePage'
import About from '@/pages/About'
import CreateTicket from '@/pages/CreateTicket'
import ticket from '@/pages/ticket'


import GetAllTickets from '@/components/GetAllTickets'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/world',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/hello',
      name: 'HelloWorld2',
      component: HelloWorld2
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/about',
      name: 'About',
      component: About
    },
    {
      path: '/createticket',
      name: 'CreateTicket',
      component: CreateTicket
    },
    {
      path: '/ticketss/:id',
      name: 'tickets',
      component: ticket
    },
    
    { path: '/', component: HomePage }
  ]
})
