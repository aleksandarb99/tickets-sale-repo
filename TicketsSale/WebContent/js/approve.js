Vue.component("approve", {
  data: function () {
    return {
      manifestations: null,
      chosenManifestation: null,
    };
  },
  mounted: function () {
    let loggedUser = localStorage.getItem("user");
    if (loggedUser == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    if (Object.keys(JSON.parse(loggedUser)).length != 7) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    this.getInactiveManifestations();
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  methods: {
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
    getInactiveManifestations: function () {
      axios
        .get("/TicketsSale/rest/manifestations/inactive")
        .then((response) => {
          this.manifestations = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    approveManifestation: function (manifestation) {
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
        .post("/TicketsSale/rest/manifestations/approve", sendData)
        .then((response) => {
          if (response.data != null) {
            this.getInactiveManifestations();
          } else {
            alert("Manifestacija je mozda vec izmenja!");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  template: ` 
          <div style="min-height:90vh;" class="container">
            <div v-if="manifestations != null && manifestations != undefined && Object.keys(manifestations).length == 0" class="alert alert-info" role="alert">
              There's no any inactive manifestations!
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
                  <th scope="col">Tickets</th>
                  <th scope="col">Logical Delete</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(m,index) in manifestations">
                  <td scope="row">{{index + 1}}</td>
                  <td>{{m.name}}</th>
                  <td>{{m.type}}</td>
                  <td>{{m.numberOfSeats}}</td>
                  <td>{{m.priceOfRegularTicket}}</td>
                  <td>{{m.date | dateFormat('DD.MM.YYYY')}}</td>
                  <td>{{m.state}}</td>
                  <td>{{m.location.address}}</td>
                  <td><a @click="approveManifestation(m)"><img style="height:30px;width:60px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF32Nl4zE14X9V8-N-TvjU8cmnuOZsecgBuw&usqp=CAU"></img></a></td>
                  <td><button v-if="checkIfDeleted(m)" @click="del($event,m)" type="button" class="btn btn-secondary btn-sm">Delete</button></td>
                  </tr>
               </tbody>
            </table>
          </div>
          `,
});
