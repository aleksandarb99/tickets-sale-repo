Vue.component("users", {
  data: function () {
    return {
      users: null,
    };
  },
  mounted: function () {
    let a = localStorage.getItem("user");
    if (a == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }

    let user = JSON.parse(a);
    //test zato sto admin ima 6 propertija

    if (Object.keys(user).length == 6) {
      axios
        .get("/TicketsSale/rest/users", this.queryParams)
        .then((response) => {
          this.users = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
    }
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  template: ` 
      <div class="container">
        <table class="table table-secondary table-striped">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Username</th>
              <th scope="col">Name</th>
              <th scope="col">LastName</th>
              <th scope="col">Gender</th>
              <th scope="col">Date of birth</th>
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
            </tr>
          </tbody>
        </table>
      </div>
      `,
});
