angular
.module('app.directives')
.directive "mmAwacModalFieldText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html"
    replace: true
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->
        $scope.controlField = () ->
            if $scope.getInfo().field.match($scope.getInfo().validationRegex)
                $scope.getInfo().isValid = true
            else
                $scope.getInfo().isValid = false

    link: (scope) ->
        directiveService.autoScopeImpl scope
