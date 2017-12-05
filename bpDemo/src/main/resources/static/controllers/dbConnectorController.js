app.controller('dbConnectorController', function($rootScope, $log, $http, $location) {
  this.ime = $rootScope.logedData.ime;
  this.greska = false;
  this.dbConnectionParams = {
    host : "",
    port : "",
    sid : "",
    db_user_username : "",
    db_user_password : ""
  };
  this.connectionSuccess = {
    showModal : false,
    success : false,
    headerResponse : "",
    paragraphResponse : ""
  };
  dbcCtrl = this;
  this.modal = {
    "modal" : true,
    "modal open" : false
  };
  this.establishConnection = function ()  {

    if (!this.greska) {
      $http.post("http://localhost:8080/jdbc/establishConnection", this.dbConnectionParams
      ).then(function successResponse(sucResponse)  {
        dbcCtrl.connectionSuccess.headerResponse = "Dobrodošao " + sucResponse.data.imeKorisnika;
        dbcCtrl.connectionSuccess.paragraphResponse = "Uspješno ste se konektovali na bazu " + sucResponse.data.imeKorisnika + " !";
        dbcCtrl.connectionSuccess.success = true;
        dbcCtrl.modal['modal'] = false;
        dbcCtrl.modal['modal open'] = true;
        dbcCtrl.connectionSuccess.showModal = true;
      }, function errorResponse(errResponse)  {
        dbcCtrl.connectionSuccess.headerResponse = "Greška !";
        dbcCtrl.connectionSuccess.paragraphResponse = errResponse.data.message;
        dbcCtrl.connectionSuccess.success = false;
      });
      dbcCtrl.connectionSuccess.showModal = true;
    }

  };
  this.zatvoriModal = function () {
    dbcCtrl.connectionSuccess.showModal = false;
    dbcCtrl.modal['modal'] = true;
    dbcCtrl.modal['modal open'] = false;

    if (dbcCtrl.connectionSuccess.success)  {
      $location.path('/dbObjectsPage');
    }
  }
});
