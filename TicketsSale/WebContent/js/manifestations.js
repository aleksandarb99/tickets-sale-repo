Vue.component("manifestations", {
  data: function () {
    return {
      manifestations: null,
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
  template: ` 
        <div class="container">
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
              </tr>
            </tbody>
          </table>
        </div>
        `,
});
