Vue.component("our-header", {
  data: function () {
    return {
      activeUser: false,
    };
  },
  methods: {
    logout: function (event) {
      event.preventDefault();
      axios.get("/TicketsSale/rest/users/logout").then((response) => {
        this.activeUser = false;
        localStorage.removeItem("user");
        window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      });
    },
  },
  mounted: function () {
    if (localStorage.getItem("user") == null) {
      this.activeUser = false;
    } else {
      this.activeUser = true;
    }
  },
  template: ` 
  <nav style="min-height:10vh;" class="container-fluid navbar navbar-expand-md navbar-dark bg-dark mb-4">
    <a  href="http://127.0.0.1:9001/TicketsSale/#/"><img style="height:9vh;" src="img/ticket.png" class="rounded" alt="..." /></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item active">
          <a class="btn btn-outline-light" href="http://127.0.0.1:9001/TicketsSale/#/">Home</a>
        </li>
        <li v-if="activeUser" class="nav-item active">
          <a class="btn btn-outline-light" href="http://127.0.0.1:9001/TicketsSale/#/profile">Profile</a>
        </li>
      </ul>
      <ul class="nav justify-content-end">
          <li v-if="!activeUser" class="nav-item">
            <a class="btn btn-outline-primary" href="http://127.0.0.1:9001/TicketsSale/#/login">Sign In</a>
          </li>
          <li v-if="!activeUser" class="nav-item">
            <a class="btn btn-outline-light" href="http://127.0.0.1:9001/TicketsSale/#/register">Register</a>
          </li>
          <li v-if="activeUser" class="nav-item">
            <button @click="logout" type="button" class="btn btn-outline-primary">Log out</button>
          </li>
        </ul>
    </div>
</nav>
  `,
});
