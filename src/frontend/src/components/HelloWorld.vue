<template>
  <h1>test: {{ msg }}</h1>

</template>

<script>

export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: ''
    }
  },
  mounted() {
    fetch("http://localhost:1112/api/messages/hello")
      .then((response) => response.text())
      .then((data) => {
          this.msg = data;
      });

  }
}

</script>
