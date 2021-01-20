Vue.component("login", {
  data: function () {
    return {
      user: null,
    };
  },
  template: ` 
  <div id="login-form" class="container"> 
    <form>
      <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
      <label for="inputUsername" class="visually-hidden">Username</label>
      <input type="text" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
      <label for="inputPassword" class="visually-hidden">Password</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <hr>
      <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    </form>
  </div>	  
  `,
});
