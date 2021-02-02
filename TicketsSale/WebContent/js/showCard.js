Vue.component("show-card", {
  name: "show-card",
  data: function () {
    return {
      mapIsSettled: false,
      manifestation: {
        name: null,
        location: { address: null },
        date: null,
      },
      mapIsBuilt: false,
      type: { name: "", discount: 0 },
      loggedIn: false,
      order: {
        type: "1",
        price: 0,
        quantity: 1,
      },
    };
  },
  methods: {
    decreateSeats: function (quantity) {
      let data = JSON.parse(localStorage.getItem("backupData"));

      for (const m of data.manifestations) {
        m.name == this.manifestation.name
          ? (m.numberOfSeats -= quantity)
          : (m.numberOfSeats -= 0);
      }

      for (const m of data.sortedFilteredManifestations) {
        m.name == this.manifestation.name
          ? (m.numberOfSeats -= quantity)
          : (m.numberOfSeats -= 0);
      }

      localStorage.setItem("backupData", JSON.stringify(data));
    },
    line: function () {
      return "a";
    },
    addDiscountPoints: function () {
      let points = (this.order.price / 1000) * 133;
      axios
        .post("/TicketsSale/rest/users/addPoints/", points.toString(), {
          headers: {
            "Content-Type": "text/plain",
          },
        })
        .then((response) => {
          this.sendOrder();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkIfStatusIsChanged: function (type) {
      if (this.type.name != type) {
        this.type = type;
      }
    },
    sendOrder: function () {
      let order = {
        quantity: this.order.quantity,
        type: this.order.type,
        price: this.order.price,
        manifestation: this.manifestation.name,
      };

      axios
        .post("/TicketsSale/rest/tickets/order/", order)
        .then((response) => {
          this.checkIfStatusIsChanged(response.data.type);
          localStorage.setItem("user", JSON.stringify(response.data));
          this.manifestation.numberOfSeats -= order.quantity;
          this.decreateSeats(order.quantity);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    orderTickets: function () {
      this.addDiscountPoints();
    },
    setPrice: function () {
      this.order.price =
        this.manifestation.priceOfRegularTicket *
        (1 - this.type.discount / 100);
    },
    editPrice: function () {
      if (this.order.type == "1") {
        this.order.price =
          this.order.quantity * this.manifestation.priceOfRegularTicket;
      } else if (this.order.type == "2") {
        this.order.price =
          4 * this.order.quantity * this.manifestation.priceOfRegularTicket;
      } else {
        this.order.price =
          2 * this.order.quantity * this.manifestation.priceOfRegularTicket;
      }
      this.order.price = this.order.price * (1 - this.type.discount / 100);
    },
  },
  mounted: function () {
    axios
      .get("/TicketsSale/rest/manifestations/".concat(this.$route.params.name))
      .then((response) => {
        this.manifestation = response.data;
      });

    let a = localStorage.getItem("user");
    if (a != null) {
      let user = JSON.parse(a);

      if (Object.keys(user).length == 11) {
        this.type = user.type;

        axios
          .get("/TicketsSale/rest/tickets")
          .then((response) => {
            this.loggedIn = true;
            this.setPrice();
          })
          .catch((err) => {
            console.log(err);
          });
      }
    }
  },
  beforeUpdate: function () {
    if (this.mapIsSettled) return;

    this.mapIsSettled = true;

    let longitude = this.manifestation.location.longitude;
    let latitude = this.manifestation.location.latitude;

    var map = new ol.Map({
      target: "map",
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM(),
        }),
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([longitude, latitude]),
        zoom: 10,
        maxZoom: 12,
        minZoom: 6,
      }),
    });

    var markers = new ol.layer.Vector({
      source: new ol.source.Vector(),
      style: new ol.style.Style({
        image: new ol.style.Icon({
          anchor: [0.5, 1],
          src: "./img/marker.png",
        }),
      }),
    });
    map.addLayer(markers);

    var marker = new ol.Feature(
      new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude]))
    );
    markers.getSource().addFeature(marker);
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  template: ` 
    <div> 
      <div class="p-4 p-md-5 mb-4 text-white rounded bg-secondary">
        <div class="col-md-6 px-0">
          <h1 class="display-4 font-italic">{{manifestation.name}}</h1>
        </div>
      </div>
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-6">
            <p class="lead my-3">Type : {{manifestation.type}}</p>
            <p class="lead my-3">Date : {{manifestation.date | dateFormat('DD.MM.YYYY')}}</p>
            <p class="lead my-3">Location : {{manifestation.location.address}}</p>
            <p class="lead my-3">Number of seats : {{manifestation.numberOfSeats}}</p>
            <p class="lead my-3">Price : {{manifestation.priceOfRegularTicket}}</p>
            <p class="lead my-3">State : {{manifestation.state}}</p>
          </div>
          <div class="col-md-6">
            <img :src="manifestation.url" class="img-fluid" alt="..." height="250" />
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <button v-if="loggedIn && manifestation.state == 'ACTIVE' && manifestation.numberOfSeats > 0" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
              Buy ticket
            </button>
            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Buying tickets</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <p class="lead my-3">Choose type of ticket</p>
                    <select @change="editPrice" v-model="order.type" aria-label="Type" aria-describedby="inputGroup-sizing-lg">
                      <option selected value="1">REGULAR</option>
                      <option value="2">VIP</option>
                      <option value="3">FAN_PIT</option>
                    </select>
                    <p class="lead my-3">Quantity:</p>
                    <input @change="editPrice" v-model="order.quantity" type="number" name="quantity" id="quantity" placeholder="Quantity" />
                    <p class="lead my-3">Price : {{manifestation.priceOfRegularTicket}} x {{order.quantity}} - {{type.discount}}%({{type.name}}) = {{order.price}}</p>
                    <div v-if="order.quantity > manifestation.numberOfSeats" class="alert alert-dark" role="alert">
                      There are not that many tickets
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button :disabled="order.quantity > manifestation.numberOfSeats ? true : false"  @click="orderTickets" type="button" data-bs-dismiss="modal" class="btn btn-primary">Buy</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div style="width:50%;height:400px;" class="col-md-6" id="map">
          </div>
        </div>
      </div>
    </div>	  
    `,
});
