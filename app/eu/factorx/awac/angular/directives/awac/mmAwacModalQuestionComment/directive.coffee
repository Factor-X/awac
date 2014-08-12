#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalQuestionComment", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html"
    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.close = ->
            modalService.close modalService.QUESTION_COMMENT

        $scope.comment = $scope.ngParams.comment

        $scope.save = ->
            $scope.ngParams.save($scope.comment)
            $scope.close()

    link: (scope) ->

