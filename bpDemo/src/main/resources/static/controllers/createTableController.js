app.controller("addTableController", function($log) {
  ctrl = this;

  this.table = {
    name : "",
    columns : [{
      columnName : "",
      dataType : "",
      keyType : "",
      nullable : false,
      unique : false,
      autoIncrement : false
    }, {
      columnName : "",
      dataType : "",
      keyType : "",
      nullable : false,
      unique : false,
      autoIncrement : false
    }]
  };
  this.dataTypes = ["NUMBER", "CHAR", "VARCHAR"];
  this.keyTypes = ["Primary key", "Foreign key", "None"];
  this.columnArray=[{"nesto": "nsdadsasds"}, {"nesto":"nsdasdan"}];

  this.ispisi = function()  {
    $log.log(ctrl.table.columns[0]);
  };
  this.add=function(){
    console.log("log");
    ctrl.columnArray.push("");
  };

  this.remove=function(idx) {
    ctrl.columnArray.splice(idx,1);x
  };

  $(document).ready(function() {
      // Select - Single
      $('select:not([multiple])').material_select();
    });
});
