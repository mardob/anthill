<template>
  <div>
    <layout-basic/>
    <p>Welcome Home</p>
    <get-all-tickets/>
  </div>
</template>

<script>


  import layoutBasic from '@/templates/LayoutBasic' 
  import getAllTickets from '@/components/GetAllTickets'
export default {
  components:{
    layoutBasic,
    getAllTickets
  },
  name: 'login',
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