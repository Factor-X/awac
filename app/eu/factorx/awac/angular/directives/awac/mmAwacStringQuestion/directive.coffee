angular
.module('app.directives')
.directive "mmAwacStringQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap:'='
    templateUrl: "$/angular/templates/mm-awac-string-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(),scope.getRepetitionMap())



        scope.$watch 'ngCondition', () ->
          if scope.getCondition()== false
            scope.getAnswerValue().value = null

