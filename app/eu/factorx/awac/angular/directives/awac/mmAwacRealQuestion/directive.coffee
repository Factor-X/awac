angular
.module('app.directives')
.directive "mmAwacRealQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap:'='
    templateUrl: "$/angular/templates/mm-awac-real-question.html"
    replace: true
    link: (scope) ->
      directiveService.autoScopeImpl scope

      scope.getAnswerValue=() ->
        return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode,scope.ngRepetitionMap)

      scope.$watch 'ngCondition', () ->
        if scope.ngCondition== false
          scope.getAnswerValue().value = null
