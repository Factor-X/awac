angular.module('app.controllers').controller("UserManagerCtrl", function($scope, translationService) {
  return $scope.title = translationService.get('USER_MANAGER_TITLE');
});