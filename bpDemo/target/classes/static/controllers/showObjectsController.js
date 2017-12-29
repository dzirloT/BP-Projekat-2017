app.controller('showObjectsController', function ($log, $http, $location, $routeParams) {

  showObjCtrl = this;
  this.objectNames = [];


  $http.post('http://localhost:8080/jdbc/getObjectNames/' + $routeParams.objectsName).then(
    function successResponse(succResponse) {
      $log.log(succResponse.data);
      showObjCtrl.objectNames = succResponse.data;
    }, function errorReponse(errResponse)  {

    }
  );

  this.showObject = function (object) {
    $location.path('showObjectsPage/' + $routeParams.objectsName + "/" + object);
  }
  this.addIndex = function (object){
    $location.path('createIndex/' + object);
  }
});
