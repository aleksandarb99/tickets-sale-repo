Vue.component("card", {
  props: ["location", "name", "date", "image"],
  methods: {
    showCard: function () {
      this.$emit("displayCard", this.name);
    },
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  template: ` 
    <div class="col zoom">
        <a :href="'http://127.0.0.1:9001/TicketsSale/#/showCard/'.concat(name)">
          <div class="card shadow-sm text-dark bg-light" v-on:click="showCard">
            <img class="bd-placeholder-img card-img-top" :src="image" alt="" width="100%" height="225" >
            <div class="card-body">
                <h5 class="card-title">{{name}}</h5>
                <p class="card-text">{{location}}.</p>
                <p class="card-text">{{date | dateFormat('DD.MM.YYYY')}}.</p>
            </div>
          </div>
        </a>
    </div>
    `,
});
