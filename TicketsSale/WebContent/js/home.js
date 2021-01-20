Vue.component("home", {
  methods: {
    displayCard: function (name) {
      //uzmi name i stavi u data da imamo selected neki i onda kad otvori rutu da se prikaze selected
      this.selectedManifestation = name;
    },
  },
  data: function () {
    return {
      manifestations: null,
      selectedManifestation: null,
    };
  },
  mounted: function () {
    if (this.manifestations == null) {
      axios
        .get("/TicketsSale/rest/manifestations")
        .then((response) => {
          this.manifestations = response.data;
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  template: ` 
  <main >
    <section class="py-5 text-center container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Welcome to Ticket Sales</h1>
          <p class="lead text-muted">“Without music life would be a mistake. ” - Friedrich Nietzsche</p>
        </div>
      </div>
    </section>

    <div class="album py-5 bg-light">
      <div class="container">

        <div v-for="item in manifestations" class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        
          <card @displayCard="displayCard" :name="item.name" :location="item.location.address" :date="item.date" :image="item.url"></card>
          
        </div>
      </div>
    </div>
  </main>		  
  `,
});
