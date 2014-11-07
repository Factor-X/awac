angular
.module('app.controllers')
.controller "AdminTranslationCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu