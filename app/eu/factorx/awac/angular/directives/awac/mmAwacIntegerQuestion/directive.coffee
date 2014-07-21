angular
.module('app.directives')
.directive "mmAwacIntegerQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap:'='
    templateUrl: "$/angular/templates/mm-awac-integer-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(),scope.getRepetitionMap())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null



        scope.$watch 'ngCondition', () ->
          if scope.getCondition()== false
            scope.getAnswerValue().value = null

