angular
.module('app.directives')
.directive "mmAwacModalFieldText", (directiveService) ->
  restrict: "E"
  scope: directiveService.autoScope
    ngInfo:'='
  templateUrl: "$/angular/templates/mm-awac-modal-field-text.html"
  replace:true
  controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

    $scope.controlField = () ->
      if $scope.getInfo().field.length>4 && $scope.getInfo().field.length< 20
        $scope.getInfo().isValid=true
      else
        $scope.getInfo().isValid=false

  link: (scope) ->
    directiveService.autoScopeImpl scope
