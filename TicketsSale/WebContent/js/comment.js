Vue.component("comment", {
  props: ["username", "text", "grade"],
  template: ` 
          <div class="comment-container">
            <p>{{username}}</p>
            <p>{{text}}</p>
            <div>
                <img class="star" v-for="num in grade" src="./img/star.png" />
            </div>
          </div>
        `,
});
