angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap:'='
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue=() ->
          return scope.$parent.getAnswerOrCreate(scope.ngQuestionCode,scope.ngRepetitionMap)

        scope.getOptionsByQuestionCode = () ->
          codeList =scope.$parent.getCodeList(scope.ngQuestionCode)
          if codeList
            return codeList.codeLabels
          return null

        scope.$watch 'ngCondition', () ->
          if scope.ngCondition== false
            scope.getAnswerValue().value = null
