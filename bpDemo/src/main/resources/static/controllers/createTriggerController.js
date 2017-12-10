app.controller('triggerController', function($log, $http, $location, validateCreateTrigger){


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
    if (this.triggerPodaci.variable=="" || this.triggerPodaci.variable.length ==0) this.triggerPodaci.variable=null;
    if(validateCreateTrigger.validate(this.triggerPodaci)  == false) {
    $http.post("http://localhost:8080/jdbc/createTrigger", this.triggerPodaci
  ).then(function successResponse(sucResponse)  {
      $log.log(succResponse.data);
  }, function errorResponse(errResponse)  {
    $log.log(errorResponse.data);
  });
}  else {
  this.greska = true;
  this.greske = validateCreateTrigger.validate(this.triggerPodaci);
  $log.log(this.greske);
};
};
});
