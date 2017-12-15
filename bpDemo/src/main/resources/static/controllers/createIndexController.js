app.controller('createIndexController', function($log, $http, $location, $routeParams, validateCreateIndex){

  this.indexData = {
    title : "",
    table : $routeParams.objectName,
    columns: [],
  };
  this.error = false;
  this.errors = {
    title : "",
    columns: [],
  };
  ctrl = this;
  ctrl.columns = [];

  this.createIndexSuccess = {
     showModal : false,
     success : false,
     headerResponse : "",
     paragraphResponse : ""
   };
   this.modal = {
     "modal" : true,
     "modal open" : false
   };

  $http.post('http://localhost:8080/jdbc/getTableColumns/' + $routeParams.objectName
  ).then(function successResponse(sucResponse)  {
    $log.log(sucResponse.data);
    ctrl.columns=sucResponse.data;
    $(document).ready(function() {
       $('select').material_select();
     });
    $log.log(ctrl.columns);
  }, function errorResponse(errResponse)  {
    $log.log(errorResponse.data);
  });

  $log.log(this.indexData.table);

  this.createIndex = function() {
    $log.log(this.indexData);
    this.error = false;
    if (validateCreateIndex.validate(this.indexData)  == false) {
      $http.post("http://localhost:8080/jdbc/createIndex", this.indexData
      ).then(function successResponse(sucResponse)  {
        ctrl.createIndexSuccess.paragraphResponse = "Indeks uspjesno kreiran!";
        ctrl.createIndexSuccess.success = true;
        ctrl.modal['modal'] = false;
        ctrl.modal['modal open'] = true;
        ctrl.createIndexSuccess.showModal = true;
        $log.log(sucResponse.data);
      }, function errorResponse(errResponse)  {
        ctrl.createIndexSuccess.headerResponse = "Greška !";
        ctrl.createIndexSuccess.paragraphResponse = errResponse.data.message;
        $log.log(errorResponse.data);
      });
    ctrl.createIndexSuccess.showModal = true;
    } else {
      this.error = true;
      this.errors = validateCreateIndex.validate(this.indexData);
      $log.log(this.errors);
    };
  };
  this.zatvoriModal = function () {
    ctrl.createIndexSuccess.showModal = false;
    ctrl.modal['modal'] = true;
    ctrl.modal['modal open'] = false;
  }
});
