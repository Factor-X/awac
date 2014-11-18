angular
.module('app.directives')
.directive "mmAwacRepetitionAddButton", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionSetCode: '='
        ngIteration: '='
    templateUrl: "$/angular/templates/mm-awac-repetition-add-button.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.isQuestionLocked = ->
            if scope.$parent.getQuestionSetLocker(scope.getQuestionSetCode())? || scope.$parent.getValidatorByQuestionCode(scope.getQuestionSetCode())?
                return true
            return false

        scope.addIteration = ->
            scope.$parent.addIteration(scope.getQuestionSetCode(),scope.getIteration())