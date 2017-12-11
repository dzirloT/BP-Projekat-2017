app.service('validateCreateTrigger', function($log)  {

  this.greska = false;
  this.greske = {
    title : "",
    okidanje : "",
    akcija : "",
    table : "",
    variable : "",
    kod : "",
    exception : ""
  };
  this.numberReg = /\d+/;
  this.validate = function(triggerPodaci) {
    if(triggerPodaci.title == "" || triggerPodaci.title.match(this.numberReg)) {
      this.greske.title = "Prazno polje naziva ili se pojavljuju brojevi";
      this.greska = true;
    }
    if(triggerPodaci.table == "") {
      this.greske.okidanje = "Morate odabrati opciju";
      this.greska = true;
    }
    if(triggerPodaci.okidanje == "") {
      this.greske.okidanje = "Morate odabrati opciju";
      this.greska = true;
    }
    if(triggerPodaci.akcija.length == 0) {
      this.greske.akcija = "Morate odabrati opciju";
      this.greska = true;
    }
    if(triggerPodaci.kod == "" || triggerPodaci.kod.length <= 20) {
      this.greske.kod = "Tekst ne može biti prazan";
      this.greska = true;
    }
    if(!this.greska)
      return false;
    return this.greske;
  };

});
