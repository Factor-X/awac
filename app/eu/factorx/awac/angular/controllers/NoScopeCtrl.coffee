angular
.module('app.controllers')
.controller "NoScopeCtrl", ($scope, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu