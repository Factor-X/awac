angular
.module('app.directives')
.directive "mmAwacModalLoading", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html"
    controller: ($scope, modalService) ->

        $scope.close= ->
            modalService.close modalService.LOADING

    link: (scope) ->

