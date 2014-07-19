angular
.module('app.directives')
.directive "mmAwacRealWithUnitQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue=() ->
          return scope.$parent.getAnswer(scope.ngQuestionCode)

        scope.getUnitsByQuestionCode = () ->
            result = scope.$parent.getUnitCategories(scope.ngQuestionCode)
            if result
              return result.units
            return null