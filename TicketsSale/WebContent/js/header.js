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
  <div class="container-fluid bg-dark">
    <div class="text-left">
      <a href="http://127.0.0.1:9001/TicketsSale/#/"><img src="img/ticket.png" class="rounded" alt="..." height="120" /></a>
    </div>
    <ul class="nav justify-content-end">
      <li v-if="!activeUser" class="nav-item">
        <a class="nav-link btn btn-primary  btn-lg" href="http://127.0.0.1:9001/TicketsSale/#/login">Sign In</a>
      </li>
      <li v-if="!activeUser" class="nav-item">
        <a class="nav-link btn btn-secondary  btn-lg" href="http://127.0.0.1:9001/TicketsSale/#/register">Register</a>
      </li>
      <li v-if="activeUser" class="nav-item">
        <button @click="logout" type="button" class="nav-link btn btn-secondary  btn-lg">Log out</button>
      </li>
    </ul>
	</div>
  `,
});
