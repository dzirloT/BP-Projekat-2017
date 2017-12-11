app.controller('showObjectController', function($http, $log, $routeParams)  {
  showObjCtrl = this;

  this.meta = [];
  this.objectDescName = {
    objectName : $routeParams.objectsName,
    name : $routeParams.objectName
  };

  $http.post("http://localhost:8080/jdbc/getObjectsMetaData", this.objectDescName).then(
    function successResponse(succResponse)  {
      $log.log(succResponse.data);
      for (var i = 0; i < succResponse.data.metaValues.length; i++) {
        showObjCtrl.meta.push({metaDataValue : succResponse.data.metaValues[i], metaDataColumnName : succResponse.data.metaKeys[i]});
      }
      for (var i = 0; i < showObjCtrl.meta.length; i++) {
        $log.log(showObjCtrl.meta[i]);
      }
    //  showObjCtrl.metaDataValues = succResponse.data.metaValues;
    //  showObjCtrl.metaDataColumnNames = succResponse.data.metaKeys;
    }, function errorResponse(errResponse)  {

    }
  );
});
