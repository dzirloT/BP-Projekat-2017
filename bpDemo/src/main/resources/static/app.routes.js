app.config(function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: "podstranice/login.html",
    controller: "loginController",
    controllerAs: "loginCtrl"
  }).when('/registration', {
    templateUrl: "podstranice/registration.html",
    controller: "registrationController",
    controllerAs: "registrationCtrl"
  }).when('/mainPage', {
    templateUrl: "podstranice/mainPage.html",
    controller: "mainPageController",
    controllerAs: "mainPageCtrl"
  }).when('/', {
    templateUrl: "podstranice/login.html",
    controller: "loginController",
    controllerAs: "loginCtrl"
  })
});
