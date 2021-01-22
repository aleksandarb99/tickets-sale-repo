Vue.component("login", {
  data: function () {
    return {
      username: null,
      password: null,
      activeUser: false,
    };
  },
  mounted: function () {
    if (sessionStorage.getItem("user") == null) {
      this.activeUser = false;
    } else {
      this.activeUser = true;
    }
    if (this.activeUser) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
    }
  },
  template: ` 
  <div id="login-form" class="container"> 
    <form class="form-reg-log" v-on:submit.prevent="checkData">
      <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
      <label for="inputUsername" class="visually-hidden">Username</label>
      <input v-model="username" type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
      <label for="inputPassword" class="visually-hidden">Password</label>
      <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <hr>
      <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    </form>
  </div>	  
  `,
  methods: {
    checkData: function () {
      let sendData = this.username + "?" + this.password;
      axios
        .post("/TicketsSale/rest/users/logIn/", sendData, {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          if (response.data == "") {
            alert("Neispravna sifra ili lozinka");
            this.username = null;
            this.password = null;
            return;
          }
          sessionStorage.setItem("user", JSON.stringify(response.data));
          this.username = null;
          this.password = null;
          window.location.href =
            "http://127.0.0.1:9001/TicketsSale/index.html#/";
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
