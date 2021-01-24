Vue.component("show-card", {
  name: "show-card",
  data: function () {
    return {
      manifestation: {
        name: null,
        location: { address: null },
        date: null,
      },
    };
  },
  mounted: function () {
    axios
      .get("/TicketsSale/rest/manifestations/".concat(this.$route.params.name))
      .then((response) => {
        this.manifestation = response.data;
      });
  },
  beforeUpdate: function () {
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
    <div class="container-fluid"> 
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
          <div style="width:50%;height:400px;" class="col-md-6" id="map">
          </div>
        </div>
      </div>
    </div>	  
    `,
});
