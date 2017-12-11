app.controller('registrationController', function($log, $http, $location, validateRegistration) {

  this.registrationData = {
    imeKorisnika: "",
    prezimeKorisnika: "",
    korisnickiMail: "",
    korisnickiPassword: ""
  };
  this.ponovljeniPassword = "";
  this.greska = false;
  this.greske = {
    imeGreska : "",
    prezimeGreska: "",
    mailGreska: "",
    passwordGreska: ""
  };

  this.registerUser = function()  {
    this.greska = false;

    if(validateRegistration.validate(this.registrationData, this.ponovljeniPassword)  == false) {
      $http.post("http://localhost:8080/korisnici/register", this.registrationData
    ).then(function successResponse(sucResponse)  {

    }, function errorResponse(errResponse)  {
      if(errResponse.data.message === "Mail")  {
        this.greska = true;
      }
    });
  } else {
    this.greska = true;
    this.greske = validateRegistration.validate(this.registrationData, this.ponovljeniPassword);
    $log.log(this.greske);
    this.ponovljeniPassword = "";
  }
};
});
