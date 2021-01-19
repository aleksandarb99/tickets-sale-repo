Vue.component("home", {
  props: ["manifestations"],
  methods: {
    displayCard: function (name) {
      alert("home alert");
      this.$emit("displayCard", name);
    },
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

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

          <card @displayCard="displayCard" name="Sasa Matic" location="Spens Novi Sad" date="30.01.2021" image="https://www.novosti.rs/upload/images/2020/04/15n/sasa-matic-tciric.jpg"></card>
          <card></card>
          <card></card>
          <card></card>
          <card></card>
          <card></card>
          
        </div>
      </div>
    </div>
  </main>		  
  `,
});
