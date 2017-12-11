app.service('validateRegistration', function($log)  {

  this.greske = {
    imeGreska : "",
    prezimeGreska: "",
    mailGreska: "",
    passwordGreska: ""
  };
  this.greska = false;
  this.numberReg = /\d+/;
  //Minimum eight characters, at least one letter and one number:
  this.passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
  this.mailReg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  this.validate = function(registrationData, ponovljeniPassword) {
    if(registrationData.imeKorisnika == "" || registrationData.imeKorisnika.match(this.numberReg)) {
      this.greske.imeGreska = "Prazno polje imena ili se pojavljuju brojevi";
      this.greska = true;
    }
    if(registrationData.prezimeKorisnika == "" || registrationData.prezimeKorisnika.match(this.numberReg)) {
      this.greske.prezimeGreska = "Prazno polje prezimena ili se pojavljuju brojevi";
      this.greska = true;
    }
    if(!registrationData.korisnickiMail.match(this.mailReg)) {
      this.greske.mailGreska = "Prazno polje maila ili ne odgovara format";
      this.greska = true;
    }
    if(!registrationData.korisnickiPassword.match(this.passwordReg)){
      $log.log(registrationData.korisnickiPassword);
      this.greske.passwordGreska = "Password mora sadrzavati 8 karaktera, barem jedno slovo i jedan broj";
      this.greska = true;
    } else if(!registrationData.korisnickiPassword === this.ponovljeniPassword) {
      this.greske.passwordGreska = "Passwordi se ne poklapaju";
      this.greska = true;
    }
    if(!this.greska)
      return false;
    return this.greske;
  };

});
