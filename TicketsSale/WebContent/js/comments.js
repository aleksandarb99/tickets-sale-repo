Vue.component("comments", {
  data: function () {
    return {
      comments: null,
    };
  },
  mounted: function () {
    let loggedUser = localStorage.getItem("user");
    if (loggedUser == null) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
      return;
    }
    let size = Object.keys(JSON.parse(loggedUser)).length;
    if (size == 6 || size == 7) {
      //Ako je admin ili prodavac
      this.getComments();
      return;
    }
    window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  methods: {
    getComments: function () {
      axios
        .get("/TicketsSale/rest/comments/")
        .then((response) => {
          this.comments = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    /*approveManifestation: function (manifestation) {
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
    },*/
  },
  template: ` 
            <div style="min-height:90vh;" class="container">
              <div v-if="comments != null && comments != undefined && Object.keys(comments).length == 0" class="alert alert-info" role="alert">
                There's no any comments yet!
              </div>
              <table v-else class="table table-secondary table-striped">
                <thead>
                  <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Customer username</th>
                    <th scope="col">Manifestation name</th>
                    <th scope="col">Text</th>
                    <th scope="col">Grade</th>
                    <th scope="col">State</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(c,index) in comments">
                    <td>{{c.id}}</th>
                    <td>{{c.customer.username}}</td>
                    <td>{{c.manifestation.name}}</td>
                    <td>{{c.text}}</td>
                    <td>{{c.grade}}</td>
                    <td>{{c.state}}</td>
                  </tr>
                 </tbody>
              </table>
            </div>
            `,
});
