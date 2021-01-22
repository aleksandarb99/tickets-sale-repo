Vue.component("home", {
  methods: {
    updateView: function () {
      this.sortedFilteredManifestations = this.filterState(
        this.filterType(this.sort(this.manifestations))
      );
    },
    filterState: function (data) {
      let tmp;
      if (this.status == "1") {
        tmp = data;
      }
      if (this.status == "2") {
        tmp = data.filter((m) => m.numberOfSeats > 0);
      }
      if (this.status == "3") {
        tmp = data.filter((m) => m.numberOfSeats == 0);
      }
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
    filterType: function (data) {
      let tmp;
      if (this.type == "1") {
        tmp = data;
      }
      if (this.type == "2") {
        tmp = data.filter((m) => m.type == "CONCERT");
      }
      if (this.type == "3") {
        tmp = data.filter((m) => m.type == "FESTIVAL");
      }
      if (this.type == "4") {
        tmp = data.filter((m) => m.type == "THEATER");
      }
      if (this.type == "5") {
        tmp = data.filter((m) => m.type == "OTHERS");
      }
      if (tmp == undefined) {
        tmp = [];
      }
      return tmp;
    },
    sort: function (data) {
      if (this.sorter == "1") {
        return data.sort((a, b) => (a.name > b.name ? 1 : -1));
      }
      if (this.sorter == "2") {
        return data.sort((a, b) => (a.name < b.name ? 1 : -1));
      }

      if (this.sorter == "3") {
        return data.sort((a, b) => (a.date > b.date ? 1 : -1));
      }
      if (this.sorter == "4") {
        return data.sort((a, b) => (a.date < b.date ? 1 : -1));
      }

      if (this.sorter == "5") {
        return data.sort((a, b) =>
          a.priceOfRegularTicket > b.priceOfRegularTicket ? 1 : -1
        );
      }
      // cini mi se da ne radi
      if (this.sorter == "6") {
        return data.sort((a, b) =>
          a.priceOfRegularTicket < b.priceOfRegularTicket ? 1 : -1
        );
      }

      if (this.sorter == "7") {
        return data.sort((a, b) =>
          a.location.address > b.location.address ? 1 : -1
        );
      }
      if (this.sorter == "8") {
        return data.sort((a, b) =>
          a.location.address < b.location.address ? 1 : -1
        );
      }
    },
    search: function () {
      axios
        .post("/TicketsSale/rest/manifestations", this.queryParams)
        .then((response) => {
          this.manifestations = response.data;
          this.updateView();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    displayCard: function (name) {
      this.selectedManifestation = name;
      let backupData = {
        sorter: this.sorter,
        type: this.type,
        status: this.status,
        queryParams: this.queryParams,
        manifestations: this.manifestations,
        selectedManifestation: this.selectedManifestation,
        sortedFilteredManifestations: this.sortedFilteredManifestations,
      };
      sessionStorage.setItem("backupData", JSON.stringify(backupData));
    },
    reset: function () {
      sessionStorage.clear();

      this.manifestations = null;
      this.selectedManifestation = null;
      this.sorter = "4";
      this.type = "1";
      this.status = "1";
      this.queryParams.name = "";
      this.queryParams.location = "";
      this.queryParams.priceFrom = "";
      this.queryParams.priceUntil = "";
      this.queryParams.dateFrom = "";
      this.queryParams.dateUntil = "";
      this.sortedFilteredManifestations = null;

      this.search();
    },
  },
  data: function () {
    return {
      sortedFilteredManifestations: null,
      manifestations: null,
      selectedManifestation: null,
      sorter: "4",
      type: "1",
      status: "1",
      queryParams: {
        name: "",
        location: "",
        priceFrom: "",
        priceUntil: "",
        dateFrom: "",
        dateUntil: "",
      },
    };
  },
  mounted: function () {
    if (sessionStorage.getItem("backupData") != null) {
      let data;
      let flag = true;
      try {
        //test
        data = JSON.parse(sessionStorage.getItem("backupData"));
        data.manifestations;
      } catch (error) {
        sessionStorage.clear();
        flag = false;
      }
      if (flag) {
        this.manifestations = data.manifestations;
        this.selectedManifestation = data.selectedManifestation;
        this.sorter = data.sorter;
        this.type = data.type;
        this.status = data.status;
        this.queryParams = data.queryParams;
        this.sortedFilteredManifestations = data.sortedFilteredManifestations;
      }
    }

    this.search();
  },
  template: ` 
  <main >
    <section class="py-5 text-center container">
      <div class="row py-lg-5">
        <div class="col-lg-6 col-md-8 mx-auto">
          <h1 class="fw-light">Welcome to Ticket Sales</h1>
          <p class="lead text-muted">“Without music life would be a mistake. ” - Friedrich Nietzsche</p>
        </div>
      </div>

      <form action="" @submit.prevent="search">
        <div class="container bg-dark wrapper">
          <div class="row">
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Name</span>
                <input v-model="queryParams.name" class="del-margins" type="text" name="name" id="name" placeholder="Name" />
              </div>
            </div>
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Location</span>
                <input v-model="queryParams.location" class="del-margins" type="text" name="location" id="location" placeholder="Location" />
              </div>
            </div>
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Price Min</span> 
                <input v-model="queryParams.priceFrom" class="del-margins" type="number" name="priceFrom" id="priceFrom" placeholder="Minimum" />
              </div>
            </div>
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Price Max</span>
                <input v-model="queryParams.priceUntil" class="del-margins" type="number" name="priceUntil" id="priceUntil" placeholder="Maximum" />
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Date From</span>
                <input v-model="queryParams.dateFrom" class="del-margins" type="date" name="dateFrom" id="dateFrom" placeholder="From" />
              </div> 
            </div>
            <div class="col-md-3">
              <div class="input-group input-group-sm mb-3">
                <span class="input-group-text" id="inputGroup-sizing-sm">Date Until</span>
                <input v-model="queryParams.dateUntil" class="del-margins" type="date" name="dateUnti" id="dateUnti" placeholder="Until" />
              </div>
            </div>
            <div class="col-md-3">
              <button @click="reset" type="button" class="del-margins btn btn-secondary">Reset</button>
            </div>
            <div class="col-md-3">
              <input class="del-margins btn btn-primary btn-lg" type="submit" name="search">Search</input>
            </div>
          </div>
        </div>
      </form> 
      <hr/>

      <div class="container">
        <div class="row">
          <div class="col-md-3">

            <div class="input-group input-group-sm mb-3">
              <span class="input-group-text" id="inputGroup-sizing-sm">Sorter</span>
              <select @change="updateView" v-model="sorter" id="inputSort" class="form-control form-select" aria-label="Sorter" aria-describedby="inputGroup-sizing-lg">
                <option value="1">By Name Ascending</option>
                <option value="2">By Name Descending</option>
                <option value="3">By Date Ascending</option>
                <option selected value="4">By Date Descending</option>
                <option value="5">By Price Ascending</option>
                <option value="6">By Price Descending</option>
                <option value="7">By Location Ascending</option>
                <option value="8">By Location Descending</option>
              </select>
            </div>
          </div>

          <div class="col-md-3">
            <div class="input-group input-group-sm mb-3">
              <span class="input-group-text" id="inputGroup-sizing-sm">Type</span>
              <select @change="updateView" v-model="type" id="inputType" class="form-control form-select" aria-label="Type" aria-describedby="inputGroup-sizing-lg">
                <option selected value="1">All</option>
                <option value="2">Concert</option>
                <option value="3">Festival</option>
                <option value="4">Theatrical Play</option>
                <option value="5">Others</option>
              </select>
            </div>      
          </div>

          <div class="col-md-3">
            <div class="input-group input-group-sm mb-3">
              <span class="input-group-text" id="inputGroup-sizing-sm">Status</span>
              <select @change="updateView" v-model="status" id="inputStatus" class="form-control form-select" aria-label="Status" aria-describedby="inputGroup-sizing-lg">
                <option selected value="1">All</option>
                <option value="2">Unsold</option>
                <option value="3">Sold Out</option>
              </select>
            </div>      
          </div>

        </div>
      </div>
    </section>
    <hr/>

    <div class="album py-5 bg-light">
      <div class="container">

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        
          <card v-for="item in sortedFilteredManifestations" @displayCard="displayCard" :name="item.name" :location="item.location.address" :date="item.date" :image="item.url"></card>
          
        </div>

        <div v-if="sortedFilteredManifestations!=null && sortedFilteredManifestations.length == 0" class="alert alert-dark" role="alert">
          Manifestation which meets these parameters does not exist
        </div>

      </div>
    </div>
  </main>		  
  `,
});
