define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacModalFieldText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacModalFieldText/template.html"
    replace: true
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->
        $scope.controlField = () ->
            if $scope.getInfo().field.length > 4 && $scope.getInfo().field.length < 20
                $scope.getInfo().isValid = true
            else
                $scope.getInfo().isValid = false

    link: (scope) ->
        directiveService.autoScopeImpl scope
