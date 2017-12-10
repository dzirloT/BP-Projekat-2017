app.controller('viewController', function($log, $http, $location,validateCreateView){

    this.viewPodaci = {
    title : "",
    tabele : "",
    kolone : "",
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
    $('#kolone').val('New Text');
      $('#kolone').trigger('autoresize');
      $('#tabele').val('New Text');
        $('#tabele').trigger('autoresize');
  this.createView = function() {
    $log.log(  this.viewPodaci);
    this.greska = false;
    if(validateCreateView.validate(this.viewPodaci)  == false) {
    $http.post("http://localhost:8080/jdbc/createView", this.viewPodaci
  ).then(function successResponse(sucResponse)  {
      $log.log(succResponse.data);
  }, function errorResponse(errResponse)  {
    $log.log(errorResponse.data);
  });
}  else {
  this.greska = true;
  this.greske = validateCreateView.validate(this.viewPodaci);
  $log.log(this.greske);
};
};



/*  this.tableNames = ["l"];
  this.columnNames=[];
    this.columnNameK=[];
  $http.post('http://localhost:8080/jdbc/getObjectNames/user_tables').then(
    function successResponse(succResponse) {
      $log.log(succResponse.data);
      this.tableNames = succResponse.data;

    }, function errorReponse(errResponse)  {
      this.tableNames = ["joJ"];

    }
  );

  $http.post('http://localhost:8080/jdbc/getTableColumnNames').then(
    function successResponse(succResponse) {
      $log.log(succResponse.data);
      this.columnNames = succResponse.data;

    }, function errorReponse(errResponse)  {
this.columnNames = ["joJ.da"];
$digest();
    }
  );



  $(document).ready(function() {
     $('select').material_select();
   });*/




/*  this.jsFunction = function(){

 $log.log("tabele");
 $log.log( this.viewPodaci.tabele);
 $log.log("kolone");
 $log.log(this.columnNameK);
  for(j=0;j<  this.viewPodaci.tabele.length;j++){
  for (i = 0; i < this.columnNameK.length; i++) {
    if (this.columnNameK[i].indexOf(this.viewPodaci.tabele[j])>=0) { this.columnNames.push(this.columnNameK[i]);$digest(); }
}
}
$log.log("potrebna");
$log.log(this.columnNames);
};

*/


});
