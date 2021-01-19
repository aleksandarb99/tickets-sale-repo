Vue.component("card", {
  props: ["location", "name", "date", "image"],
  methods: {
    showCard: function () {
      alert("card alert");
      this.$emit("displayCard", this.name);
    },
  },
  template: ` 
    <div class="col">
        <div class="card shadow-sm" v-on:click="showCard">
            <img class="bd-placeholder-img card-img-top" :src="image" alt="" width="100%" height="225" >
            <div class="card-body">
                <p class="card-text">Name : {{name}}.</p>
                <p class="card-text">Location : {{location}}.</p>
                <p class="card-text">Date : {{date}}.</p>
            </div>
        </div>
    </div>
    `,
});
