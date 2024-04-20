<template>
  <div class="getAllTickets">
    <!--<br>
    <input type=button v-on:click="getTickets" placeholder="getTickets" value="getTickets"><br><br> -->
    <h5>All tickets avaliable in system</h5>
    <ul id="example-1">
      <li v-for="(ticket, index) in tickets._embedded.tickets" :key="ticket.name">
        <router-link :to="'/ticketss/' + index" exact class="navbar-item" title="Ticket">
          <b>{{index}}</b>
          <b>Name:</b> {{ ticket.name }}, <b>description:</b> {{ ticket.description }}, <b>dateCreated:</b> {{ ticket.dateCreated }},
          <b>lastChecked:</b> {{ ticket.lastChecked }}, <b>lastUpdated:</b> {{ ticket.lastUpdated }}, <b>state:</b> {{ ticket.state }} 
        </router-link>
      </li>
    </ul>   
  </div>
</template>

<script>

export default {
  name: 'homePage',
  data() {
    return {
      tickets: { "_embedded": { "tickets": [ ] }}
    }
  },
  mounted() {
     fetch(this.$backendUrlRoot +  "tickets")
        .then((response) => response.json())
        .then((json) => {
          this.tickets = json;
           console.log(json);
        });
  },
  methods: {
    getTickets: function (event) {
        fetch(this.$backendUrlRoot + "tickets")
        .then((response) => {
          response.json();
          if (!response.ok) {
            // get error message from body or default to response statusText
            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
          }
        })
        .then((json) => {
          this.tickets = json;
           console.log(json);
      });
    }
  }
  
}

</script>