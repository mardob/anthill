<template>
  <div class="getAllTickets">
    <!--<br>
    <input type=button v-on:click="getTickets" placeholder="getTickets" value="getTickets"><br><br> -->
    <h5>Notes: </h5> {{ ticketId }}
    <ul id="example-1">
      <li v-for="note in notes._embedded.notes" :key="note.name">
        <b>Name:</b> {{ note.name }}, <b>description:</b> {{ note.description }}, <b>dateCreated:</b> {{ note.dateCreated }},
        <b>lastUpdated:</b> {{ note.lastUpdated }} 
      </li>
    </ul>   
  </div>
</template>

<script>

export default {
  name: 'getRelatedNotes',

  props: {'ticketId': Number},

  data() {
    return {
      notes: { "_embedded": { "notes": [ ] }},      
    }
  },

 

  mounted() {
    fetch(this.$backendUrlRoot + "tickets/" + this.ticketId + "/notes")
      .then((response) => response.json())
      .then((json) => {
        this.notes = json;
        console.log(json);
      });
      
  }
  
}

</script>