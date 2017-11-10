app.controller("navBarController", function($rootScope, $scope, $http, $log, $location)  {

  $rootScope.logedData = {
    loged : false,
    ime : "",
    prezime : "",
    mail : ""
  };

  if(localStorage.getItem('loged') != null) {
    $rootScope.logedData.loged = localStorage.getItem('loged');
    $rootScope.logedData.ime = localStorage.getItem('ime');
    $rootScope.logedData.prezime = localStorage.getItem('prezime');
    $rootScope.logedData.mail = localStorage.getItem('mail');

    $location.path("/mainPage");
  }

  $scope.odjava = function()  {
    $rootScope.logedData.loged = false;
    $rootScope.logedData.ime = "";
    $rootScope.logedData.prezime = "";
    $rootScope.logedData.mail = "";

    localStorage.removeItem('loged');
    localStorage.removeItem('ime');
    localStorage.removeItem('prezime');
    localStorage.removeItem('mail');

    $location.path("/login");
  };
});
