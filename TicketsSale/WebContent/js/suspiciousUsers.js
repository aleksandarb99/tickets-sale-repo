Vue.component("susUsers", {
  methods: {
    cancel: function (event, ticket) {
      for (const t of this.tickets) {
        if (t.id == ticket.id) {
          ticket.state = "CANCELED";
        }
      }
      for (const t of this.shownTickets) {
        if (t.id == ticket.id) {
          ticket.state = "CANCELED";
        }
      }

      let user = JSON.parse(localStorage.getItem("user"));
      user.tickets = this.tickets;

      let lostPoints = (ticket.price / 1000) * 133 * 4;
      user.collectedPoints = user.collectedPoints - lostPoints;
      localStorage.setItem("user", JSON.stringify(user));

      event.target.disabled = true;

      axios
        .post("/TicketsSale/rest/tickets/cancelTicket/", ticket.id, {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          ticket.state = "CANCELED";
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  data: function () {
    return {
      users: [],
    };
  },
  mounted: function () {
    let user = localStorage.getItem("user");
    if (user == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    if (Object.keys(JSON.parse(user)).length != 6) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    axios
      .get("/TicketsSale/rest/users/susUsers/")
      .then((response) => {
        this.users = response.data;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  template: ` 
        <div class="container " style="min-height:90vh;">
            <div v-if="users != null && users != undefined && Object.keys(users).length == 0" class="alert alert-info" role="alert">
                There are no users with these parameters!
            </div>
            <div v-else>   
                <table v-if="users != null && users != undefined && Object.keys(users).length != 0" id="users" class="table table-secondary table-striped">
                    <thead>
                        <tr>
                        <th scope="col">#</th>
                        <th scope="col">Username</th>
                        <th scope="col">Name</th>
                        <th scope="col">lastName</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Date of birth</th>
                        <th scope="col">Role</th>
                        <th scope="col">Collected Points</th>
                        <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(user,index) in users">
                        <td scope="row">{{index + 1}}</td>
                        <td>{{user.username}}</th>
                        <td>{{user.name}}</td>
                        <td>{{user.lastName}}</td>
                        <td>{{user.gender}}</td>
                        <td>{{user.dateOfBirth | dateFormat('DD.MM.YYYY')}}</td>
                        <td>{{user.role}}</td>
                        <td>{{user.points}}</td>
                        <td><button @click="block($event,t)" type="button" class="btn btn-secondary btn-sm">Block</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>  
        </div>
        `,
});
