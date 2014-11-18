angular
.module('app.directives')
.directive "mmAwacModalAgreement", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-agreement.html"
    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.url = "http://www.w3schools.com" # #" + $scope.$root.instanceName + "/agreement_" + $scope.$root.language + ".html"

        $scope.close = ->
            modalService.close modalService.HELP

    link: (scope) ->

