const Login = { template: "<login></login>" };
const Home = { template: "<home></home>" };
const Register = { template: "<register></register>" };
const ShowCard = { template: "<show-card></show-card>" };
const Profile = { template: "<profile></profile>" };

const router = new VueRouter({
  name: "router",
  mode: "hash",
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
    { path: "/showCard/:name", component: ShowCard },
    { path: "/profile", component: Profile },
  ],
});

var app = new Vue({
  router,
  name: "Ticket Sales",
  el: "#root",
});
