Vue.component("our-header", {
  template: ` 
  <div class="container-fluid bg-dark">
    <div class="text-left">
      <a href="http://127.0.0.1:5500/index.html#/"><img src="/img/ticket.png" class="rounded" alt="..." height="120" /></a>
    </div>
    <ul class="nav justify-content-end">
      <li class="nav-item">
        <a class="nav-link btn btn-primary  btn-lg" href="http://127.0.0.1:5500/index.html#/login">Sign In</a>
      </li>
      <li class="nav-item">
        <a class="nav-link btn btn-secondary  btn-lg" href="http://127.0.0.1:5500/index.html#/register">Register</a>
      </li>
    </ul>
	</div>
  `,
});
