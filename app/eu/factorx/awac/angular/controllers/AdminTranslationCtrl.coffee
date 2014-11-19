angular
.module('app.controllers')
.controller "AdminTranslationCtrl", ($scope, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.state = "interface"
    $scope.toState = (s) ->
        $scope.state = s
        return false

