#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalVerificationFinalizationVisualization", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization-visualization.html"
    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.close = ->
            modalService.close modalService.VERIFICATION_FINALIZATION_VISUALIZATION

        $scope.comment = $scope.ngParams.comment

    link: (scope) ->

