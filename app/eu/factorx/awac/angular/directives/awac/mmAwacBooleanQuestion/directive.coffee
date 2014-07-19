angular
.module('app.directives')
.directive "mmAwacBooleanQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html"
    replace: true
    link: (scope) ->
      directiveService.autoScopeImpl scope

      scope.getAnswerValue=() ->
        return scope.$parent.getAnswer(scope.ngQuestionCode)
