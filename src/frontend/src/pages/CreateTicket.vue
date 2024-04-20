<template>
  <div>
    <layout-basic/>
    <h3>Create ticket</h3>


    <form>
      <div class="form-group">
        <label for="ticketName">Name:</label>
        <input type="text" v-model="ticketName" class="form-control" id="ticketName" aria-describedby="nameHelp" placeholder="Enter name of the ticket">
        <small id="nameHelp" class="form-text text-muted">Add name of new ticket, this should be simple description of the problem.</small>
      </div>
      <div class="form-group">
        <label for="ticketDescription">Description:</label>
        <textarea class="form-control" v-model="ticketDesc" id="ticketDescription" aria-describedby="descriptionHelp" rows="3" placeholder="Enter description of the ticket"></textarea>
        <small id="descriptionHelp" class="form-text text-muted">Add description of the new ticket, please add as many usefull information as possible to the ticket.</small>
      </div>
              
      <label v-if="sucess && newTicket != ''"  >Item sucesfully created</label>
      <label v-if="!sucess && newTicket != ''" >Item creation failed</label> 
   
      <button type="submit" @click="createTicket()" class="btn btn-primary">Create ticket</button>
    </form>
    
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
      ticketName: '',
      ticketDesc: '',
      userId: '1',
      newTicket: '',
      sucess: false
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
    },
    createTicket: function(){
      console.log("Trying to create ticket with following data: " + this.ticketName +' || '+ this.ticketDesc);
      
      const requestOptions = {
        method: "POST",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify({ name: this.ticketName,  description: this.ticketDesc})
      };
      console.log("Request send to address: " +this.$backendUrlRoot + "tickets" +"; body: " + JSON.stringify({ name: this.ticketName,  description: this.ticketDesc}));
      
      fetch(this.$backendUrlRoot + "tickets", requestOptions)
        .then((response) => response.json())
        .then((json) => {
        //  this.tickets = json;
          console.log("response json: ");
          console.log(json);
          this.newTicket = json;
          if(json.error == ""){
            this.sucess = false;
          }else{
            this.sucess = true;
          }
          
      });
    }
  }

}

</script>