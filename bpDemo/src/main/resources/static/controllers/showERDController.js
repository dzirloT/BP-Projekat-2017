app.controller('showERDController', function($scope, $http, $log, download,$rootScope) {
  erdCtrl = this;
  erdTables = [];
  erdRelations = [];

    $http.post("http://localhost:8080/jdbc/showERD").then(
      function successResponse(succResponse)  {
        $log.log("Radi showERD");
        $log.log(succResponse);

        for (var i = 0; i < succResponse.data.length; i++) {
          this.erdTables.push({
            'key': succResponse.data[i].tableName,
            items: []
          });
          for (var j = 0; j < succResponse.data[i].columns.length; j++) {
            this.erdTables[i].items.push({
              name: succResponse.data[i].columns[j].columnName,
              iskey: false,
              figure: "Cube1",
              color: "black"
            })
          }
          if(succResponse.data[i].relations.length != 0){
            for (var j = 0; j < succResponse.data[i].relations.length; j++) {
              erdRelations.push({
                from: succResponse.data[i].tableName,
                to: succResponse.data[i].relations[j].toTable
              });
            }
          }
        }
        $log.log(this.erdRelations);
        myDiagram = new go.GraphLinksModel(this.erdTables, this.erdRelations);
        $scope.model = myDiagram;
      }, function errorResponse(errResponse)  {
        $log.log(errResponse);
      }
    );

    $scope.save = function(){

      var img = $rootScope.diagram.makeImageData({
        scale: 1,
        background: "AntiqueWhite"
      });
      $log.log(img);
      download.fromData(img, "image/jpeg", "ERD.jpeg");
    };

});
