angular
.module('app.directives')
.directive "mmAwacModalHelp", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-help.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        if $scope.getParams().template?
            $scope.url = "$/angular/views/" + $scope.$root.instanceName + "/" + $scope.getParams().template + "_" + $scope.$root.language + ".html"

        $scope.close = ->
            modalService.close modalService.HELP

    link: (scope) ->

