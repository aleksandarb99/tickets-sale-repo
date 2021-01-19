const Login = { template: "<login></login>" };
const Home = { template: "<home></home>" };

const router = new VueRouter({
  mode: "hash",
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
  ],
});

var app = new Vue({
  router,
  name: "Ticket Sales",
  el: "#root",
});
