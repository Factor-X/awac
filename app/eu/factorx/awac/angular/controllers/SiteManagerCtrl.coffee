angular
.module('app.controllers')
.controller "SiteManagerCtrl", ($scope,translationService) ->

    $scope.toForm = ->
        $scope.$parent.navToLastFormUsed()