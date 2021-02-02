const Login = { template: "<login></login>" };
const Home = { template: "<home></home>" };
const Register = { template: "<register></register>" };
const ShowCard = { template: "<show-card></show-card>" };
const Profile = { template: "<profile></profile>" };
const Users = { template: "<users></users>" };
const Manifestations = { template: "<manifestations></manifestations>" };
const AddSeller = { template: "<addSeller></addSeller>" };
const Tickets = { template: "<tickets></tickets>" };
const Approve = { template: "<approve></approve>" };
const Comments = { template: "<comments></comments>" };
const ApproveReject = {
  template: "<rejectApproveComments></rejectApproveComments>",
};

const router = new VueRouter({
  name: "router",
  mode: "hash",
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
    { path: "/showCard/:name", component: ShowCard },
    { path: "/profile", component: Profile },
    { path: "/users", component: Users },
    { path: "/approve", component: Approve },
    { path: "/manifestations", component: Manifestations },
    { path: "/comments", component: Comments },
    { path: "/rejectApproveComments", component: ApproveReject },
    { path: "/addSeller", component: AddSeller },
    { path: "/tickets", component: Tickets },
  ],
});

var app = new Vue({
  router,
  name: "Ticket Sales",
  el: "#root",
});
