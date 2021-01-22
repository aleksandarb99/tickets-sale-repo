Vue.component("register", {
  data: function () {
    return {
      user: { gender: "CHOOSE" },
      activeUser: false,
    };
  },
  mounted: function () {
    if (localStorage.getItem("user") == null) {
      this.activeUser = false;
    } else {
      this.activeUser = true;
    }
    if (this.activeUser) {
      window.location.href = "http://127.0.0.1:9001/TicketsSale/index.html#/";
    }
  },
  template: `
  <div id="login-form" class="container"> 
    <form class="form-reg-log" v-on:submit.prevent="checkData">
      <h1 class="h3 mb-3 fw-normal">Please register</h1>
      <label for="inputUsername" class="visually-hidden">Username</label>
      <input v-model="user.username" type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
      <label for="inputPassword" class="visually-hidden">Password</label>
      <input v-model="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <label for="inputName" class="visually-hidden">Name</label>
      <input v-model="user.name" type="text" id="inputName" class="form-control" placeholder="Name" required>
      <label for="inputSurname" class="visually-hidden">Surname</label>
      <input v-model="user.lastName" type="text" id="inputSurname" class="form-control" placeholder="Surname" required>

      <select v-model="user.gender" id="inputGender" class="form-control form-select" aria-label="Gender">
        <option selected value="CHOOSE">Open this select menu</option>
        <option value="MALE">Male</option>
        <option value="FEMALE">Female</option>
      </select>

      <input v-model="user.dateOfBirth" class="form-control" type="date" name="inputDate" data-placeholder="   Date of birth" required aria-required="true" />

      <hr>
      <button class="w-100 btn btn-lg btn-primary" type="submit">Register</button>
    </form>
    <div v-if="user.gender == 'CHOOSE'" class="alert alert-info" role="alert">
      U must choose another gender!
    </div>
  </div>
    `,
  methods: {
    checkData: function () {
      if (this.user.gender == "CHOOSE") {
        return;
      }

      let parts = this.user.dateOfBirth.split("-");
      this.user.dateOfBirth = new Date(
        parts[0],
        parts[1] - 1,
        parts[2]
      ).getTime();
      axios
        .post("/TicketsSale/rest/users/customer/", this.user)
        .then((response) => {
          if (response.data == "") {
            alert(
              "Korisnik sa korisnickim imenom:" +
                this.user.username +
                " vec postoji!"
            );
            this.user = {};
            return;
          }
          alert(
            "Uspesno ste se registrovali kao korisnik: " + this.user.username
          );
          this.user = {};
          window.location.href =
            "http://127.0.0.1:9001/TicketsSale/index.html#/";
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
});
