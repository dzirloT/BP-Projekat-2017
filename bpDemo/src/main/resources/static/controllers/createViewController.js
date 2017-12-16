app.controller('viewController', function($log, $http, $location,validateCreateView){
ctrl=this;
    this.viewPodaci = {
    title : "",
    tabele : [],
    kolone : [],
    uslov : ""
  };
    this.greska = false;
    this.greske = {
    title : "",
    tabele : [],
    kolone : [],
    uslov : ""
  };
  $('#uslov').val('New Text');
    $('#uslov').trigger('autoresize');

    this.createViewSuccess = {
       showModal : false,
       success : false,
       headerResponse : "",
       paragraphResponse : ""
     };
     this.modal = {
       "modal" : true,
       "modal open" : false
     };

  this.createView = function() {
    $log.log(  this.viewPodaci);
    this.greska = false;
    if(validateCreateView.validate(this.viewPodaci)  == false) {
    $http.post("http://localhost:8080/jdbc/createView", this.viewPodaci
  ).then(function successResponse(sucResponse)  {
  ctrl.createViewSuccess.paragraphResponse = "Pogled uspjesno kreiran!";
  ctrl.createViewSuccess.success = true;
  ctrl.modal['modal'] = false;
  ctrl.modal['modal open'] = true;
  ctrl.createViewSuccess.showModal = true;
  $log.log(sucResponse.data);
  }, function errorResponse(errResponse)  {
    ctrl.createViewSuccess.headerResponse = "Greška !";
    ctrl.createViewSuccess.paragraphResponse = errResponse.data.message;
    $log.log(errorResponse.data);
  });
}  else {
  this.greska = true;
  this.greske = validateCreateView.validate(this.viewPodaci);
  $log.log(this.greske);
};
};

this.zatvoriModal = function () {
  ctrl.createViewSuccess.showModal = false;
  ctrl.modal['modal'] = true;
  ctrl.modal['modal open'] = false;
}


 ctrl.tableNames = [];
  ctrl.columnNames=[];
    ctrl.columnNameK=[];
  $http.post('http://localhost:8080/jdbc/getObjectNames/user_tables').then(
    function successResponse(succResponse) {
      $log.log(succResponse.data);
      ctrl.tableNames = succResponse.data;
      $(document).ready(function() {
       $('select').material_select();
     });

    }, function errorReponse(errResponse)  {


    }
  );

  $http.post('http://localhost:8080/jdbc/getTableColumnNames').then(
    function successResponse(succResponse) {
      $log.log(succResponse.data);
      ctrl.columnNameK = succResponse.data;
      $(document).ready(function() {
       $('select').material_select();
     });
    }, function errorReponse(errResponse)  {

    }
  );



  $(document).ready(function() {
     $('select').material_select();
   });




  this.jsFunction = function(){

    ctrl.columnNames=[];
  for(j=0;j< this.viewPodaci.tabele.length;j++){
  for (i = 0; i < this.columnNameK.length; i++) {
    if (ctrl.columnNameK[i].indexOf(this.viewPodaci.tabele[j])>=0) { ctrl.columnNames.push(ctrl.columnNameK[i]);
      $(document).ready(function() {
             $('select').material_select();
           }); }
}
}

};




});
