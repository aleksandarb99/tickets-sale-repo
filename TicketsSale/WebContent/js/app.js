const Login = { template: "<login></login>" };
const Home = {
  template: "<home ></home>",
};
const Register = { template: "<register></register>" };

const router = new VueRouter({
  name: "router",
  mode: "hash",
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
  ],
  methods: {
    displayCard: function (name) {
      console.log(name);
    },
  },
});

var app = new Vue({
  router,
  name: "Ticket Sales",
  el: "#root",
  data: {
    manifestations: null,
  },
  mounted: function () {
    if (this.manifestations != null) {
      axios.get("http://localhost:9001/rest/manifestations").then((data) => {
        this.manifestations = data;
      });
    }
  },
});
