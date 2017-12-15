app.service('validateCreateIndex', function($log)  {

  this.error = false;
  this.errors = {
    title : "",
  };

  this.validate = function(indexData) {
    if(indexData.title == "") {
      this.errors.title = "Prazno polje naziva";
      this.error = true;
    }
    if(!this.error)
      return false;
    return this.errors;
  };
});
