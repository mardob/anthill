<template>
  <div>
    <layout-basic/>
    Ticket {{ $route.params.id }}
    <br>
    <b>Name:</b> {{ ticket.name }}, <b>description:</b> {{ ticket.description }}, <b>dateCreated:</b> {{ ticket.dateCreated }},
    <b>lastChecked:</b> {{ ticket.lastChecked }}, <b>lastUpdated:</b> {{ ticket.lastUpdated }}, <b>state:</b> {{ ticket.state }} 
    <br>
    <create-new-note  v-bind:ticketId="$route.params.id" />
    <br>
    <get-related-notes v-bind:ticketId="$route.params.id" />
  </div>
</template>

<script>

  import layoutBasic from '@/templates/LayoutBasic' 
  import getRelatedNotes from '@/components/GetRelatedNotes' 
  import createNewNote from '@/components/CreateNewNote' 

export default {
  name: 'login',
  components:{
    layoutBasic,
    getRelatedNotes,
    createNewNote,
  },
  data() {
    return {
      ticket: {name:"",description:"",dateCreated:"",lastChecked:"",lastUpdated:"",state:""},
    }
    
  },
  mounted() {
    //get parameter as local variable
    const url_data = this.$route.params.id;

    //use local parameter in call
    fetch(this.$backendUrlRoot + "tickets/" + url_data + "/")
    .then((response) => response.json())
    .then((json) => {
      this.ticket = json;
      console.log(json);
    });
  }

}

</script>