define ["./module"], (controllers) ->
  "use strict"
  controllers.controller "UserManagerCtrl", ($scope,translationService) ->

    $scope.title = translationService.get('USER_MANAGER_TITLE')