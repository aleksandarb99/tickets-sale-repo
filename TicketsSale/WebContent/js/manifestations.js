Vue.component("manifestations", {
  data: function () {
    return {
      chosenManifestation: null,
      manifestations: null,
      tickets: null,
    };
  },
  mounted: function () {
    let a = localStorage.getItem("user");
    if (a == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    let user = JSON.parse(a);

    if (user.manifestations != undefined) {
      this.manifestations = user.manifestations;
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
          this.tickets = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  template: ` 
        <div style="height:90vh;" class="container">
          <div v-if="manifestations != null && manifestations != undefined && Object.keys(manifestations).length == 0" class="alert alert-info" role="alert">
            You have no any manifestations!
          </div>
          <div v-else>
            <table class="table table-secondary table-striped">
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
                  <th>Tickets</th>
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
                  <td><a @click="getTickets(m)" href="#tickets"><img style="height:30px;width:60px;" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF32Nl4zE14X9V8-N-TvjU8cmnuOZsecgBuw&usqp=CAU"></img></a></td>
                </tr>
              </tbody>
            </table>
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
        </div>
        `,
});
