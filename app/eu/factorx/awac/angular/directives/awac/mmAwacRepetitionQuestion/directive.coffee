angular
.module('app.directives')
.directive "mmAwacRepetitionQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionSetCode: '='
        ngIteration:'='
        ngRepetitionMap:'='
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getQuestionSet = () ->
          return scope.$parent.getQuestionSet(scope.getQuestionSetCode())


        scope.removeAnwser = () ->
          scope.$parent.removeIteration(scope.getQuestionSetCode(),scope.getIteration(),scope.getRepetitionMap())