app.controller('showObjectsController', function ($log, $http, $location, $routeParams) {

  showObjCtrl = this;
  this.objectNames = [];
  this.tabele = false;
  if($routeParams.objectsName == 'user_tables')
    showObjCtrl.tabele = true;

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

  this.addObject = function (object){
    $location.path('createIndex/' + object);
  }
});
