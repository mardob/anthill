<template>
  <div>
  <layout-basic/>
  <div class="login" align="center">
    
      <br>
    <input v-model="userName" placeholder="User name"><br><br>
    <input type="password" v-model="password" placeholder="Password"><br><br>
    <input type=button v-on:click="login" placeholder="Login" value="Log in"><br><br>

    <p>Message is: {{ userName }}</p>

  </div>
  </div>
</template>

<script>

import layoutBasic from '@/templates/LayoutBasic' 
export default {
  name: 'login',
  components:{
    layoutBasic
  },
  data() {
    return {
      userName: '',
      password: ''
    }
  },
  methods: {
    greet: function (event) {
      // `this` inside methods point to the Vue instance
      console.log('Hello ' + this.userName + '!');
      // `event` is the native DOM event
    //  alert(event.target.tagName)
    },
    login: function (event) {
      // `this` inside methods point to the Vue instance
      console.log('Hello, ' + this.userName +' we will now check your login thouruhly!');
      if(this.password != ''){
        console.log("Welcome " + this.userName);
      }else{
        console.log("ACESS DENIED");
      }
      // `event` is the native DOM event
    //  alert(event.target.tagName)
    }
  }

}

</script>