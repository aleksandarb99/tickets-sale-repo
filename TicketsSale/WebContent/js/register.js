Vue.component("register", {
  data: function () {
    return {
      user: null,
    };
  },
  template: `
  <div id="login-form" class="container"> 
    <form>
      <h1 class="h3 mb-3 fw-normal">Please register</h1>
      <label for="inputUsername" class="visually-hidden">Username</label>
      <input type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
      <label for="inputPassword" class="visually-hidden">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <label for="inputName" class="visually-hidden">Name</label>
      <input type="text" id="inputName" class="form-control" placeholder="Name" required>
      <label for="inputSurname" class="visually-hidden">Surname</label>
      <input type="text" id="inputSurname" class="form-control" placeholder="Surname" required>
      <select id="inputGender" class="form-control form-select" aria-label="Gender">
        <option selected>Open this select menu</option>
        <option value="1">Male</option>
        <option value="2">Female</option>
      </select>
      <hr>
      <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    </form>
  </div>
    `,
});
