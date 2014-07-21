angular
.module('app.directives')
.directive "mmAwacRealWithUnitQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap: '='
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.getUnitsByQuestionCode = () ->
            result = scope.$parent.getUnitCategories(scope.getQuestionCode())
            if result
                return result.units
            return null

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.$watch 'ngCondition', () ->
            if scope.getCondition() == false
                scope.getAnswerValue().value = null