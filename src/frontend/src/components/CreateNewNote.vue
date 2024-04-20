<template>
  <div class="createNewNote">
    <form>
      <div class="form-group">
        <label for="ticketDescription">New Note:</label><br>
        <label for="ticketName">Name:</label>
        <input type="text" v-model="noteName" class="form-control" id="ticketName" aria-describedby="nameHelp" placeholder="Enter name of the ticket">
        <label for="ticketName">Description:</label>
        <textarea class="form-control" v-model="noteDesc" id="ticketDescription" aria-describedby="descriptionHelp" rows="2" placeholder="Enter note"></textarea>
        <small id="descriptionHelp" class="form-text text-muted">Add your new note.</small>
      </div>

      <button type="submit" @click="createNote()" class="btn btn-primary">Create note</button>
    </form>   
  </div>
</template>

<script>

export default {
  name: 'homePage',
  data() {
    return {
      noteName: '',
      noteDesc: '',
      ticketId: this.$route.params.id
    }
  },


  methods: {
    createNote: function(){
      
      console.log("Trying to create ticket with following data: " + this.noteName +' || '+ this.noteDesc + ' || ' + this.ticketId);
      console.log(JSON.stringify({ name: this.noteName, description: this.noteDesc, ticket: this.ticketId }));

      const requestOptions = {
        method: "POST",
         headers: { "Content-Type": "application/json" },
         body: JSON.stringify({ name: this.noteName, description: this.noteDesc })
      };
      const url = this.$backendUrlRoot + "tickets/"+this.ticketId+"/notes";

      console.log("Request send to address: " + url +"; body: " + requestOptions);

      fetch(url, requestOptions)
        .then((response) => response.json())
        .then((json) => {
           console.log("response json: ");
          console.log(json);
          this.tickets = json;
        });
     }
  }
  
}

</script>