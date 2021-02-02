Vue.component("rejectApproveComments", {
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
    approveComment: function (comment) {
      axios
        .post("/TicketsSale/rest/comments/approve", comment.id, {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          if (response.data != null) {
            this.getComments();
          } else {
            alert("Failed");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    rejectComment: function (comment) {
      axios
        .post("/TicketsSale/rest/comments/reject", comment.id, {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          if (response.data != null) {
            this.getComments();
          } else {
            alert("Failed");
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
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
                      <th scope="col"></th>
                      <th scope="col"></th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(c,index) in comments" v-if="c.state == 'IN_PROGRESS'">
                      <td>{{c.id}}</th>
                      <td>{{c.customer.username}}</td>
                      <td>{{c.manifestation.name}}</td>
                      <td>{{c.text}}</td>
                      <td>{{c.grade}}</td>
                      <td><a @click="approveComment(c)"><img style="height:32px;width:32px;" src="./img/check.png" /></a></td>
                      <td><a @click="rejectComment(c)"><img style="height:32px;width:32px;" src="./img/delete.png" /></a></td>
                    </tr>
                   </tbody>
                </table>
              </div>
              `,
});
