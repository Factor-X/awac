angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap: '='
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.getOptionsByQuestionCode = () ->
            codeList = scope.$parent.getCodeList(scope.getQuestionCode())
            if codeList
                return codeList.codeLabels
            return null

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null

        scope.$watch 'ngCondition', () ->
            if scope.getCondition() == false
                scope.getAnswerValue().value = null
