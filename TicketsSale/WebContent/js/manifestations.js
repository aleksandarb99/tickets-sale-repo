Vue.component("manifestations", {
  data: function () {
    return {
      chosenManifestation: null,
      manifestations: null,
      tickets: null,
      mode: "HIDE",
      selectedManifestation: {
        location: { id: 0 },
      },
      backup: null,
      userType: null,
    };
  },
  mounted: function () {
    let a = localStorage.getItem("user");
    if (a == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    let user = JSON.parse(a);

    if (Object.keys(user).length == 7) {
      this.userType = "ADMIN";
      this.getManifestations();
    } else if (user.manifestations != undefined) {
      this.userType = "SELLER";
      this.manifestations = this.hideDeleted(user.manifestations);
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
  methods: {
    editManifestation: function () {
      if (this.selectedManifestation.name == undefined) return;
      this.backup = [
        this.selectedManifestation.name,
        this.selectedManifestation.type,
        this.selectedManifestation.numberOfSeats,
        this.selectedManifestation.date,
        this.selectedManifestation.priceOfRegularTicket,
        this.selectedManifestation.state,
        this.selectedManifestation.location,
        this.selectedManifestation.url,
      ];
      this.mode = "EDIT";
    },
    addManifestation: function () {
      this.selectedManifestation = {
        location: { id: 0 },
      };
      this.backup = null;
      this.mode = "ADD";
    },
    cancelEditing: function () {
      this.mode = "HIDE";
      if (this.backup == null) return;
      this.selectedManifestation.name = this.backup[0];
      this.selectedManifestation.type = this.backup[1];
      this.selectedManifestation.numberOfSeats = this.backup[2];
      this.selectedManifestation.date = this.backup[3];
      this.selectedManifestation.priceOfRegularTicket = this.backup[4];
      this.selectedManifestation.state = this.backup[5];
      this.selectedManifestation.location = this.backup[6];
      this.selectedManifestation.url = this.backup[7];
      this.backup = null;
    },
    selectManifestation: function (manifestation) {
      if (this.mode == "HIDE") {
        this.selectedManifestation = manifestation;
      }
    },
    getTickets: function (manifestation) {
      this.chosenManifestation = manifestation;
      let sendData = {
        name: manifestation.name,
        type: manifestation.type,
        date: manifestation.date,
        state: manifestation.state,
        url: manifestation.url,
        priceOfRegularTicket: manifestation.priceOfRegularTicket,
        numberOfSeats: manifestation.numberOfSeats,
        location: manifestation.location,
      };
      axios
        .post("/TicketsSale/rest/tickets/", sendData)
        .then((response) => {
          this.tickets = this.hideDeleted(response.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkData: function () {
      let parts = this.selectedManifestation.date.split("T");
      let parts1 = parts[0].split("-");
      let parts2 = parts[1].split(":");
      this.selectedManifestation.date = new Date(
        parts1[0],
        parts1[1] - 1,
        parts1[2],
        parts2[0],
        parts2[1]
      ).getTime();
      let path = "/TicketsSale/rest/manifestations/";
      let sendData = { manifestation: this.selectedManifestation };
      if (this.mode == "ADD") path += "add/";
      if (this.mode == "EDIT") {
        path += "update/";
        sendData.oldName = this.backup[0];
      }
      delete sendData.manifestation.dateLong;
      axios
        .post(path, sendData)
        .then((response) => {
          if (response.data == "") {
            alert("Failed");
            this.restartData();
            return;
          }
          alert("Success!");
          this.restartData();
          window.location.href =
            "http://127.0.0.1:9001/TicketsSale/index.html#/";
        })
        .catch((err) => {
          console.log(err);
        });
    },
    restartData: function () {
      this.selectedManifestation = { location: { id: 0 } };
      this.backup = null;
      this.mode = "HIDE";
    },
    del: function (event, m) {
      event.target.disabled = true;
      axios
        .post("/TicketsSale/rest/manifestations/delete/", m.name, {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          m.deleted = true;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkIfDeleted: function (m) {
      if (m.deleted != undefined) {
        if (m.deleted) {
          return false;
        } else {
          return true;
        }
      }
    },
    getManifestations: function () {
      axios
        .get("/TicketsSale/rest/manifestations/all/")
        .then((response) => {
          this.manifestations = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    hideDeleted: function (data) {
      let tmp = data.filter((m) => m.deleted == false);
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
  },
  template: ` 
          <div style="min-height:90vh;" class="container">
            <div v-if="manifestations != null && manifestations != undefined && Object.keys(manifestations).length == 0" class="alert alert-info" role="alert">
              You have no any manifestations!
            </div>
            <table v-else class="table table-secondary table-striped">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">Name</th>
                  <th scope="col">Type</th>
                  <th scope="col">Number of seats</th>
                  <th scope="col">Regular price</th>
                  <th scope="col">Date of event</th>
                  <th scope="col">Status</th>
                  <th scope="col">Location</th>
                  <th v-if="userType=='SELLER'" scope="col">Tickets</th>
                  <th v-if="userType=='ADMIN'" scope="col">Logical Delete</th>
                </tr>
              </thead>
                <tr v-for="(m,index) in manifestations" @click="selectManifestation(m)" v-bind:class="{selected : selectedManifestation.name===m.name}">
                  <td scope="row">{{index + 1}}</td>
                  <td>{{m.name}}</th>
                  <td>{{m.type}}</td>
                  <td>{{m.numberOfSeats}}</td>
                  <td>{{m.priceOfRegularTicket}}</td>
                  <td>{{m.date | dateFormat('DD.MM.YYYY')}}</td>
                  <td>{{m.state}}</td>
                  <td>{{m.location.address}}</td>
                  <td v-if="userType=='SELLER'"><a @click="getTickets(m)" href="#tickets"><img style="height:30px;width:60px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF32Nl4zE14X9V8-N-TvjU8cmnuOZsecgBuw&usqp=CAU"></img></a></td>
                  <td v-if="userType=='ADMIN'"><button v-if="checkIfDeleted(m)" @click="del($event,m)" type="button" class="btn btn-secondary btn-sm">Delete</button></td>
                  </tr>
            </table>

            <button v-if="userType!='ADMIN'" class="btn btn-lg btn-primary" v-on:click="addManifestation">Add</button>
            <button v-if="userType!='ADMIN'" class="btn btn-lg btn-secondary" v-on:click="editManifestation" v-bind:disabled="selectedManifestation.name ==undefined && mode!='EDIT '">Edit</button>
            <br />
            <div v-if="userType!='ADMIN' && mode != 'HIDE'" class="container"> 
              <form class="form-reg-log" v-on:submit.prevent="checkData">
                <input v-model="selectedManifestation.name" type="text" id="name" class="form-control" placeholder="Name" required autofocus>
                <input v-model="selectedManifestation.numberOfSeats" type="number" id="number" class="form-control" placeholder="Number of seats" required>
                <input v-model="selectedManifestation.priceOfRegularTicket" type="number" id="price" class="form-control" placeholder="Price of regular ticket" required>
                <input v-model="selectedManifestation.url" type="text" id="url" class="form-control" placeholder="Url" required>
                <input v-model="selectedManifestation.date" type="datetime-local" class="form-control" id="date" required>
                <input v-model="selectedManifestation.location.longitude" type="number" id="longitude" class="form-control" placeholder="Longitude" required>
                <input v-model="selectedManifestation.location.latitude" type="number" id="latitude" class="form-control" placeholder="Latitude" required>
                <input v-model="selectedManifestation.location.address" type="text" id="address" class="form-control" placeholder="Address" required>

                <select v-model="selectedManifestation.type" id="type" class="form-control form-select" aria-label="Type" required>
                  <option value="CONCERT">Concert</option>
                  <option value="FESTIVAL">Festival</option>
                  <option value="THEATER">Theater</option>
                  <option value="OTHERS">Others</option>
                </select>
                <hr>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Save</button>
                <button class="w-100 btn btn-lg btn-secondary" type="button" v-on:click="cancelEditing" v-bind:disabled="mode=='HIDE'">Cancel</button> <br />
              </form>
            </div>

            <table v-if="tickets != null && tickets != undefined && Object.keys(tickets).length != 0" id="tickets" class="table table-secondary table-striped">
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
                </tr>
              </thead>
              <tbody>
                <tr v-for="(t,index) in tickets">
                  <td scope="row">{{index + 1}}</td>
                  <td>{{t.id}}</th>
                  <td>{{t.reservedManifestation.name}}</td>
                  <td>{{t.date | dateFormat('DD.MM.YYYY')}}</td>
                  <td>{{t.price}}</td>
                  <td>{{t.nameLastName}}</td>
                  <td>{{t.state}}</td>
                  <td>{{t.type}}</td>
                </tr>
              </tbody>
            </table>
            <div v-else class="alert alert-info" role="alert">
              <h3 v-if="chosenManifestation != null" >There's no any tickets about manifestation: {{chosenManifestation.name}}!</h3>
            </div>
          </div>
        `,
});
