angular
.module('app.directives')
.directive "mmAwacRealQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap: '='
    templateUrl: "$/angular/templates/mm-awac-real-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null

        scope.$watch 'ngCondition', () ->
        if scope.ngCondition == false
            scope.getAnswerValue().value = null
