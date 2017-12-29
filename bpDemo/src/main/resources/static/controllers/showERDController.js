app.controller('showERDController', function($scope, $http, $log, download,$rootScope) {
  erdCtrl = this;
  erdTables = [];
  erdRelations = [];

  $scope.model = new go.GraphLinksModel(
    [
      { key: "Products",
        items: [ { name: "ProductID", iskey: true, figure: "Decision", color: "purple" },
                 { name: "ProductName", iskey: false, figure: "Cube1", color: "purple" },
                 { name: "SupplierID", iskey: false, figure: "Decision", color: "purple" },
                 { name: "CategoryID", iskey: false, figure: "Decision", color: "purple" } ] },
      { key: "Suppliers",
        items: [ { name: "SupplierID", iskey: true, figure: "Decision", color: "purple" },
                 { name: "CompanyName", iskey: false, figure: "Cube1", color: "purple" },
                 { name: "ContactName", iskey: false, figure: "Cube1", color: "purple" },
                 { name: "Address", iskey: false, figure: "Cube1", color: "purple" } ] },
      { key: "Categories",
        items: [ { name: "CategoryID", iskey: true, figure: "Decision", color: "purple" },
                 { name: "CategoryName", iskey: false, figure: "Cube1", color: "purple" },
                 { name: "Description", iskey: false, figure: "Cube1", color: "purple" },
                 { name: "Picture", iskey: false, figure: "TriangleUp", color: "purple" } ] },
      { key: "Order Details",
        items: [ { name: "OrderID", iskey: true, figure: "Decision", color: "purple" },
                 { name: "ProductID", iskey: true, figure: "Decision", color: "purple" },
                 { name: "UnitPrice", iskey: false, figure: "MagneticData", color: "purple" },
                 { name: "Quantity", iskey: false, figure: "MagneticData", color: "purple" },
                 { name: "Discount", iskey: false, figure: "MagneticData", color: "purple" } ] }
    ],
    [
      { from: "Products", to: "Suppliers", text: "0..N", toText: "1" },
      { from: "Products", to: "Categories", text: "0..N", toText: "1" },
      { from: "Order Details", to: "Products", text: "0..N", toText: "1" }
    ]);
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
