Vue.component("tickets", {
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
    checkForDisabled: function (ticket) {
      if (ticket.state == "CANCELED") {
        return false;
      }

      let name = ticket.reservedManifestation.name;
      let date2 = null;

      let data = JSON.parse(localStorage.getItem("backupData"));
      for (const m of data.manifestations) {
        if (m.name == name) {
          date2 = new Date(parseInt(m.date));
        }
      }

      var date1 = new Date();
      let Difference_In_Time = date2.getTime() - date1.getTime();
      let Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
      if (Difference_In_Days >= 7) {
        return true;
      }
      return false;
    },
    filterState: function (data) {
      let tmp;
      if (this.status == "1") {
        tmp = data;
      }
      if (this.status == "2") {
        tmp = data.filter((m) => m.state == "RESERVED");
      }
      if (this.status == "3") {
        tmp = data.filter((m) => m.state == "CANCELED");
      }
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
    filterType: function (data) {
      let tmp;
      //all
      if (this.type == "1") {
        tmp = data;
      }
      if (this.type == "2") {
        tmp = data.filter((m) => m.type == "VIP");
      }
      if (this.type == "3") {
        tmp = data.filter((m) => m.type == "REGULAR");
      }
      if (this.type == "4") {
        tmp = data.filter((m) => m.type == "FAN_PIT");
      }
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
    sort: function (data) {
      if (this.sorter == "1") {
        return data.sort((a, b) =>
          a.reservedManifestation.name > b.reservedManifestation.name ? 1 : -1
        );
      }
      if (this.sorter == "2") {
        return data.sort((a, b) =>
          a.reservedManifestation.name < b.reservedManifestation.name ? 1 : -1
        );
      }
      if (this.sorter == "3") {
        return data.sort((a, b) => (a.price > b.price ? 1 : -1));
      }
      if (this.sorter == "4") {
        return data.sort((a, b) => (a.price < b.price ? 1 : -1));
      }
      if (this.sorter == "5") {
        return data.sort((a, b) => (a.date > b.date ? 1 : -1));
      }
      if (this.sorter == "6") {
        return data.sort((a, b) => (a.date < b.date ? 1 : -1));
      }
    },
    reset: function () {
      this.shownTickets = this.tickets;
      this.sorter = "1";
      this.type = "1";
      this.status = "1";

      this.queryParams.name = "";
      this.queryParams.priceFrom = "";
      this.queryParams.priceUntil = "";
      this.queryParams.dateFrom = "";
      this.queryParams.dateUntil = "";
    },
    updateView: function () {
      let data = this.searchFirst();
      this.shownTickets = this.filterState(this.filterType(this.sort(data)));
    },
    searchFirst: function () {
      let tmp = this.tickets;
      if (this.queryParams.name != "") {
        let param = this.queryParams.name.toLowerCase();
        tmp = tmp.filter((m) =>
          m.reservedManifestation.name.toLowerCase().startsWith(param)
        );
      }
      if (this.queryParams.priceFrom != "") {
        let param = parseFloat(this.queryParams.priceFrom);
        tmp = tmp.filter((m) => m.price > param);
      }
      if (this.queryParams.priceUntil != "") {
        let param = parseFloat(this.queryParams.priceUntil);
        tmp = tmp.filter((m) => m.price < param);
      }
      if (this.queryParams.dateFrom != "") {
        let param = this.queryParams.dateFrom;
        let parts = param.split("-");
        param = new Date(parts[0], parts[1] - 1, parts[2]).getTime();
        tmp = tmp.filter((m) => m.date > param);
      }
      if (this.queryParams.dateUntil != "") {
        let param = this.queryParams.dateUntil;
        let parts = param.split("-");
        param = new Date(parts[0], parts[1] - 1, parts[2]).getTime();
        tmp = tmp.filter((m) => m.date < param);
      }
      return tmp;
    },
  },
  data: function () {
    return {
      adminIsLoggedIn: false,
      shownTickets: null,
      tickets: null,
      sorter: "1",
      type: "1",
      status: "1",
      queryParams: {
        name: "",
        priceFrom: "",
        priceUntil: "",
        dateFrom: "",
        dateUntil: "",
      },
    };
  },
  mounted: function () {
    let a = localStorage.getItem("user");
    if (a == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    let user = JSON.parse(a);

    if (Object.keys(user).length == 6) {
      alert("ADMIN");
      adminIsLoggedIn = true;

      axios
        .get("/TicketsSale/rest/tickets")
        .then((response) => {
          this.tickets = response.data;
          this.shownTickets = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    } else if (user.tickets != undefined) {
      this.tickets = user.tickets;
      this.shownTickets = user.tickets;
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
          <div style="min-height:90vh;">

            <form action="" @submit.prevent="updateView">
            <div class="container-fluid bg-dark">
              <div class="container has-row ">
                <div class="row form-row">
                  <div class="col-md-12 col-sm-12">
                    <h3 style="color:white">CHOOSE PARAMETERS</h3>
                  </div>
                  <div class="col-md-2 col-sm-12 del-padding spread-it">
                    <div class="input-group input-group-sm mb-3 spread-it">
                      <input v-model="queryParams.name" class="del-margins spread-it" type="text" name="name" id="name" placeholder="Name" />
                    </div>
                  </div>
                  <div class="col-md-2 col-sm-6 del-padding spread-it">
                    <div class="input-group input-group-sm mb-3 spread-it">
                      <input v-model="queryParams.priceFrom" class="del-margins spread-it" type="number" name="priceFrom" id="priceFrom" placeholder="Minimum" />
                    </div>
                  </div>
                  <div class="col-md-2 col-sm-6 del-padding spread-it">
                    <div class="input-group input-group-sm mb-3 spread-it">
                      <input v-model="queryParams.priceUntil" class="del-margins spread-it" type="number" name="priceUntil" id="priceUntil" placeholder="Maximum" />
                    </div>
                  </div>
                  <div class="col-md-2 col-sm-12 del-padding spread-it">
                    <div class="input-group input-group-sm mb-3 spread-it">
                      <input  v-model="queryParams.dateFrom" class="spread-it" type="date" name="dateFrom" data-placeholder="From" />
                    </div> 
                  </div>
                  <div class="col-md-2 col-sm-12  del-padding spread-it">
                    <div class="input-group input-group-sm mb-3 spread-it">
                      <input  v-model="queryParams.dateUntil" class="spread-it" type="date" name="dateUnti" data-placeholder="Until" />
                    </div>
                  </div>
                  <div class="col-md-1 col-sm-6  del-padding spread-it">
                    <button style="width: 100%" @click="reset" type="button btn-md" class="del-margins btn btn-secondary spread-it">Reset</button>
                  </div>
                  <div class="col-md-1 col-sm-6  del-padding spread-it">
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
                    <option value="3">By Price Ascending</option>
                    <option value="4">By Price Descending</option>
                    <option value="5">By Date Ascending</option>
                    <option value="6">By Date Descending</option>
                  </select>
                </div>
              </div>
      
              <div class="col-md-3">
                <div class="input-group input-group-sm mb-3">
                  <span class="input-group-text" id="inputGroup-sizing-sm">Type</span>
                  <select @change="updateView" v-model="type" id="inputType" class="form-control form-select" aria-label="Type" aria-describedby="inputGroup-sizing-lg">
                    <option selected value="1">All</option>
                    <option value="2">VIP</option>
                    <option value="3">REGULAR</option>
                    <option value="4">FAN PIT</option>
                  </select>
                </div>      
              </div>
      
              <div class="col-md-3">
                <div class="input-group input-group-sm mb-3">
                  <span class="input-group-text" id="inputGroup-sizing-sm">Status</span>
                  <select @change="updateView" v-model="status" id="inputStatus" class="form-control form-select" aria-label="Status" aria-describedby="inputGroup-sizing-lg">
                    <option selected value="1">All</option>
                    <option value="2">RESERVED</option>
                    <option value="3">CANCELED</option>
                  </select>
                </div>      
              </div>
      
            </div>
          </div>
          <hr/>
          <div class="container">
            <div v-if="shownTickets != null && shownTickets != undefined && Object.keys(shownTickets).length == 0" class="alert alert-info" role="alert">
              You have no any tickets!
            </div>
            <div v-else>
              <table v-if="shownTickets != null && shownTickets != undefined && Object.keys(shownTickets).length != 0" id="tickets" class="table table-secondary table-striped">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Id</th>
                    <th scope="col">Manifestation</th>
                    <th scope="col">Date</th>
                    <th scope="col">Price</th>
                    <th scope="col">Customer</th>
                    <th scope="col">State</th>
                    <th scope="col">Type</th>
                    <th scope="col">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(t,index) in shownTickets">
                    <td scope="row">{{index + 1}}</td>
                    <td>{{t.id}}</th>
                    <td>{{t.reservedManifestation.name}}</td>
                    <td>{{t.date | dateFormat('DD.MM.YYYY')}}</td>
                    <td>{{t.price}}</td>
                    <td>{{t.nameLastName}}</td>
                    <td>{{t.state}}</td>
                    <td>{{t.type}}</td>
                    <td><button v-if="checkForDisabled(t)" @click="cancel($event,t)" type="button" class="btn btn-secondary btn-sm">Cancel</button></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
          `,
});
