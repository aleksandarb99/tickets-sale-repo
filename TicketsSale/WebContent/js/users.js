Vue.component("users", {
  methods: {
    filterType: function (data) {
      let tmp;
      //all
      if (this.type == "1") {
        tmp = data;
      }
      if (this.type == "2") {
        tmp = data.filter((m) => m.role == "Customer");
      }
      if (this.type == "3") {
        tmp = data.filter((m) => m.role == "Seller");
      }
      if (this.type == "4") {
        tmp = data.filter((m) => m.role == "Administrator");
      }
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
    sort: function (data) {
      if (this.sorter == "1") {
        return data.sort((a, b) => (a.name > b.name ? 1 : -1));
      }
      if (this.sorter == "2") {
        return data.sort((a, b) => (a.name < b.name ? 1 : -1));
      }
      if (this.sorter == "3") {
        return data.sort((a, b) => (a.lastName > b.lastName ? 1 : -1));
      }
      if (this.sorter == "4") {
        return data.sort((a, b) => (a.lastName < b.lastName ? 1 : -1));
      }
      if (this.sorter == "5") {
        return data.sort((a, b) => (a.username > b.username ? 1 : -1));
      }
      if (this.sorter == "6") {
        return data.sort((a, b) => (a.username < b.username ? 1 : -1));
      }
      if (this.sorter == "7") {
        return data.sort((a, b) => (a.points > b.points ? 1 : -1));
      }
      if (this.sorter == "8") {
        return data.sort((a, b) => (a.points < b.points ? 1 : -1));
      }
    },
    reset: function () {
      this.shownUsers = this.users;
      this.sorter = "1";
      this.type = "1";

      this.queryParams.name = "";
      this.queryParams.lastName = "";
      this.queryParams.username = "";
    },
    updateView: function () {
      let data = this.searchFirst();
      this.shownUsers = this.filterType(this.sort(data));
    },
    searchFirst: function () {
      let tmp = this.users;
      if (this.queryParams.name != "") {
        let param = this.queryParams.name.toLowerCase();
        tmp = tmp.filter((m) => m.name.toLowerCase().startsWith(param));
      }
      if (this.queryParams.lastName != "") {
        let param = this.queryParams.lastName.toLowerCase();
        tmp = tmp.filter((m) => m.lastName.toLowerCase().startsWith(param));
      }
      if (this.queryParams.username != "") {
        let param = this.queryParams.username.toLowerCase();
        tmp = tmp.filter((m) => m.username.toLowerCase().startsWith(param));
      }
      return tmp;
    },
  },
  data: function () {
    return {
      shownUsers: [],
      users: [],
      sorter: "1",
      type: "1",
      queryParams: {
        name: "",
        lastName: "",
        username: "",
      },
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
      .get("/TicketsSale/rest/users/customers/")
      .then((response) => {
        let customers = response.data;
        console.log(customers);
        for (const element of customers) {
          element.role = "Customer";
          element.points = element.collectedPoints;
        }

        this.users = [...customers, ...this.users];
        this.updateView();
      })
      .catch((err) => {
        console.log(err);
      });

    axios
      .get("/TicketsSale/rest/users/sellers/")
      .then((response) => {
        let sellers = response.data;
        console.log(sellers);
        for (const element of sellers) {
          element.role = "Seller";
          element.points = 0;
        }

        this.users = [...sellers, ...this.users];
        this.updateView();
      })
      .catch((err) => {
        console.log(err);
      });

    axios
      .get("/TicketsSale/rest/users/administrators/")
      .then((response) => {
        let admins = response.data;
        console.log(admins);
        for (const element of admins) {
          element.role = "Administrator";
          element.points = 0;
        }

        this.users = [...admins, ...this.users];
        this.updateView();
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

        <form action="" @submit.prevent="updateView">
          <div class="container-fluid bg-dark">
            <div class="container has-row ">
              <div class="row form-row">
                <div class="col-md-12 col-sm-12">
                  <h3 style="color:white">CHOOSE PARAMETERS</h3>
                </div>
                <div class="col-md-2 col-sm-6 del-padding spread-it">
                  <div class="input-group input-group-sm mb-3 spread-it">
                    <input v-model="queryParams.username" class="del-margins spread-it" type="text" name="username" id="username" placeholder="Username" />
                  </div>
                </div>
                <div class="col-md-2 col-sm-12 del-padding spread-it">
                  <div class="input-group input-group-sm mb-3 spread-it">
                    <input v-model="queryParams.name" class="del-margins spread-it" type="text" name="name" id="name" placeholder="Name" />
                  </div>
                </div>
                <div class="col-md-2 col-sm-6 del-padding spread-it">
                  <div class="input-group input-group-sm mb-3 spread-it">
                    <input v-model="queryParams.lastName" class="del-margins spread-it" type="text" name="lastName" id="lastName" placeholder="LastName" />
                  </div>
                </div>
                <div class="col-md-3 col-sm-6  del-padding spread-it">
                  <button style="width: 100%" @click="reset" type="button btn-md" class="del-margins btn btn-secondary spread-it">Reset</button>
                </div>
                <div class="col-md-3 col-sm-6  del-padding spread-it">
                  <input style="width: 100%"class="del-margins btn btn-primary btn-md spread-it" type="submit" name="submit"></input>
                </div>
              </div>
            </div>
          </div>
        </form> 
        <hr/>

        <div class="container">
          <div class="row">
            <div class="col-md-3">

              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Sorter</span>
                <select @change="updateView" v-model="sorter" id="inputSort" class="form-control form-select" aria-label="Sorter" aria-describedby="inputGroup-sizing-lg">
                  <option selected value="1">By Name Ascending</option>
                  <option value="2">By Name Descending</option>
                  <option value="3">By LastName Ascending</option>
                  <option value="4">By LastName Descending</option>
                  <option value="5">By Username Ascending</option>
                  <option value="6">By Username Descending</option>
                  <option value="7">By Points Ascending</option>
                  <option value="8">By Points Descending</option>
                </select>
              </div>
            </div>

            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Type</span>
                <select @change="updateView" v-model="type" id="inputType" class="form-control form-select" aria-label="Type" aria-describedby="inputGroup-sizing-lg">
                  <option selected value="1">All</option>
                  <option value="2">CUSTOMERS</option>
                  <option value="3">SELLERS</option>
                  <option value="4">ADMINS</option>
                </select>
              </div>      
            </div>

          </div>
        </div>
        <hr/>
        <div class="container">
          <div v-if="shownUsers != null && shownUsers != undefined && Object.keys(shownUsers).length == 0" class="alert alert-info" role="alert">
            There are no users with these parameters!
          </div>
          <div v-else>     

            <table v-if="shownUsers != null && shownUsers != undefined && Object.keys(shownUsers).length != 0" id="users" class="table table-secondary table-striped">
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
                </tr>
              </thead>
              <tbody>
                <tr v-for="(user,index) in shownUsers">
                  <td scope="row">{{index + 1}}</td>
                  <td>{{user.username}}</th>
                  <td>{{user.name}}</td>
                  <td>{{user.lastName}}</td>
                  <td>{{user.gender}}</td>
                  <td>{{user.dateOfBirth | dateFormat('DD.MM.YYYY')}}</td>
                  <td>{{user.role}}</td>
                  <td>{{user.points}}</td>
                </tr>
              </tbody>
            </table>

          </div>
        </div>

      </div>
      `,
});
