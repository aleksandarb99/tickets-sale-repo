Vue.component("carouselItem", {
  props: ["title", "date", "image"],
  data: function () {
    return {
      isActive: global.counter,
    };
  },
  filters: {
    dateFormat: function (value, format) {
      var parsed = moment(new Date(parseInt(value)));
      return parsed.format(format);
    },
  },
  created: function () {
    global.counter++;
  },
  template: ` 
        <div v-bind:class="[isActive == 0 ? 'active' : '', 'carousel-item']">
          <img :src="image" alt="image"></img> 
          <div class="cover-image-gradient"></div>         
          <div class="container">
            <div class="carousel-caption text-start">
                <h1 class="card-title">{{title}}</h1>
                <p class="card-text">{{date | dateFormat('DD.MM.YYYY')}}.</p>
            </div>
          </div>
        </div>
      `,
});
