app.controller('mainPageController', function($rootScope, $log) {
  this.ime = $rootScope.logedData.ime;
  $log.log($rootScope.logedData.loged);
});
