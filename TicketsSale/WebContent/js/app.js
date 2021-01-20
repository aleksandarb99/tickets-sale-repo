const Login = { template: "<login></login>" };
const Home = { template: "<home></home>" };
const Register = { template: "<register></register>" };
const ShowCard = { template: "<show-card></show-card>" };

const router = new VueRouter({
  name: "router",
  mode: "hash",
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
    { path: "/showCard", component: ShowCard },
  ],
});

var app = new Vue({
  router,
  name: "Ticket Sales",
  el: "#root",
});
