<template>
<div id="app">
    <router-view/>
  </div>
</template>



<script>
import HelloWorld from './components/HelloWorld.vue';
import HelloWorld2 from './components/HelloWorld2.vue';
import LoginPage from './pages/LoginPage';
import GetAllTickets from './components/GetAllTickets';

export default {
  
  name: 'App',
  components: {
    HelloWorld,
    HelloWorld2,
    LoginPage,
    GetAllTickets,
  },
  data () {
    return {
      userName: "",
      password: ""
    }
  }
};


</script>

<style scoped>
#app{
  margin-left: 10px;
  margin-right: 10px;
  margin-top: 5px;
}
</style>
