app.controller('loginController', function($log, $http, $location, $rootScope){


  this.korisnickiPodaci = {
    korisnickiMail : "",
    korisnickiPassword : ""
  };

  this.loginUser = function() {
    $log.log(this.korisnickiPodaci);
    $http.post("http://localhost:8080/korisnici/login", this.korisnickiPodaci
  ).then(function successResponse(sucResponse)  {
      $rootScope.logedData.loged = true;
      $rootScope.logedData.ime = sucResponse.data.ime;
      $rootScope.logedData.prezime = sucResponse.data.prezime;
      $rootScope.logedData.mail = sucResponse.data.mail;

      localStorage.setItem('loged', true);
      localStorage.setItem('ime', $rootScope.logedData.ime);
      localStorage.setItem('prezime', $rootScope.logedData.prezime);
      localStorage.setItem('mail', $rootScope.logedData.maiil);

      $location.path("/mainPage");
  }, function errorResponse(errResponse)  {
    $log.log(errorResponse.data);
  });
};
});
