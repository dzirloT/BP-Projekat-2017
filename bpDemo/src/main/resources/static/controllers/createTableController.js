app.controller("addTableController", function($log, $http) {
  ctrl = this;

  this.primaryKeyTables = [];
  $http.get('http://localhost:8080/jdbc/primaryKeys').then(
    function successResponse(succResponse)
    {
      succResponse.data.forEach(function(element){ctrl.primaryKeyTables.push(element);});
      $(document).ready(function() {
          // Select - Single
          $('select:not([multiple])').material_select();
        }); 
      $log.log(ctrl.primaryKeyTables);
    }, function errorReponse(errResponse)  {
      $log.log("NE RADI ");
      $log.log(errResponse.data);
    });


  this.uradiNesto = function(keyType, indeks)  {
    if(keyType == "FOREIGN KEY")  {
      $(document).ready(function() {
          // Select - Single
          $('select:not([multiple])').material_select();
        });
    } else {
      ctrl.table.columns[indeks].relation = {tableName : "", columnName : ""};
    }

  }
  this.table = {
    name : "",
    columns : [{
      columnName : "",
      dataType : "",
      keyType : "",
      relation : {tableName : "", columnName : ""},
      nullable : false,
      unique : false,
      autoIncrement : false
    }]
  };
  this.dataTypes = ["number", "char", "varchar2"];
  this.keyTypes = ["PRIMARY KEY", "FOREIGN KEY", "none"];

  this.ispisi = function()  {
    ctrl.table.columns.forEach(function(element) {
      $log.log(element);
    });
  };

  this.add=function(){
    ctrl.table.columns.push({
      columnName : "",
      dataType : "",
      keyType : "",
      relation : {tableName : "", columnName : ""},
      nullable : false,
      unique : false,
      autoIncrement : false
    });
    $(document).ready(function() {
        // Select - Single
        $('select:not([multiple])').material_select();
      });
  };

  this.remove=function(idx) {
    ctrl.table.columns.splice(idx,1);
  };

  this.createTable = function() {
    $http.post("http://localhost:8080/jdbc/createTable", this.table
  ).then(function successResponse(sucResponse)  {

  }, function errorResponse(errResponse)  {

  });
  };

  $(document).ready(function() {
      // Select - Single
      $('select:not([multiple])').material_select();
    });
});
