angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue=() ->
          return scope.$parent.getAnswer(scope.ngQuestionCode)

        scope.getOptionsByQuestionCode = () ->
          codeList =scope.$parent.getCodeList(scope.ngQuestionCode)
          if codeList
            return codeList.codeLabels
          return null

        if scope.ngCondition
          console.log "ma condition est vrai !!"
        else
          console.log "ma condition est fausse !!"
