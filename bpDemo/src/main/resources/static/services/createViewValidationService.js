app.service('validateCreateView', function($log)  {

  this.greska = false;
  this.greske = {
    title : "",
    tabele : "",
    kolone : "",
    uslov : ""
  };
  this.numberReg = /\d+/;
  this.validate = function(viewPodaci) {
    if(viewPodaci.title == "" || viewPodaci.title.match(this.numberReg)) {
      this.greske.title = "Prazno polje naziva ili se pojavljuju brojevi";
      this.greska = true;
    }
    if(viewPodaci.tabele == "") {
      this.greske.tabele = "Tekst ne može biti prazan";
      this.greska = true;
    }
    if(viewPodaci.kolone == "" ) {
      this.greske.kolone = "Tekst ne može biti prazan";
      this.greska = true;
    }
    if(viewPodaci.uslov == "") {
      this.greske.uslov = "Tekst ne može biti prazan";
      this.greska = true;
    }
    if(!this.greska)
      return false;
    return this.greske;
  };

});
