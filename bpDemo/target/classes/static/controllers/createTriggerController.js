app.controller('triggerController', function($log, $http, $location, validateCreateTrigger){
ctrl=this;

  this.triggerPodaci = {
    title : "",
    okidanje : "",
    akcija : [],
    table : "",
    variable : "",
    kod : "",
    red : "",

  };
  this.red = false;
  this.greska = false;
  this.greske = {
    title : "",
    okidanje : "",
    akcija : "",
    table : "",
    variable : "",
    kod : "",
  };

  this.createTriggerSuccess = {
     showModal : false,
     success : false,
     headerResponse : "",
     paragraphResponse : ""
   };
   this.modal = {
     "modal" : true,
     "modal open" : false
   };

  ctrl.tableNames = [];
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
  $(document).ready(function() {
     $('select').material_select();
   });
   $('#trigger_code').val('New Text');
     $('#trigger_code').trigger('autoresize');
  $('#variable_decl').val('New Text');
  $('#variable_decl').trigger('autoresize');


  this.createTrigger = function() {
    $log.log(this.triggerPodaci);
    this.greska = false;
    if (this.red==true) this.triggerPodaci.red ="FOR EACH ROW";
    if (this.triggerPodaci.variable=="" || this.triggerPodaci.variable.length ==0) ctrl.triggerPodaci.variable=null;
    if(validateCreateTrigger.validate(this.triggerPodaci)  == false) {
    $http.post("http://localhost:8080/jdbc/createTrigger", this.triggerPodaci
  ).then(function successResponse(sucResponse)  {
      ctrl.createTriggerSuccess.paragraphResponse = "Trigger uspjesno kreiran!";
      ctrl.createTriggerSuccess.success = true;
      ctrl.modal['modal'] = false;
      ctrl.modal['modal open'] = true;
      ctrl.createTriggerSuccess.showModal = true;
      $log.log(sucResponse.data);
  }, function errorResponse(errResponse)  {
    ctrl.createTriggerSuccess.headerResponse = "Greška !";
    ctrl.createTriggerSuccess.paragraphResponse = errResponse.data.message;
    $log.log(errorResponse.data);
  });
}  else {
  this.greska = true;
  this.greske = validateCreateTrigger.validate(this.triggerPodaci);
  $log.log(this.greske);
};
};
this.zatvoriModal = function () {
  ctrl.createTriggerSuccess.showModal = false;
  ctrl.modal['modal'] = true;
  ctrl.modal['modal open'] = false;
}
});
