angular
.module('app.directives')
.directive "mmAwacRepetitionQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionSetCode: '='
        ngIteration: '='
        ngRepetitionMap: '='
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getQuestionSet = () ->
            return scope.$parent.getQuestionSet(scope.getQuestionSetCode())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.removeAnwser = () ->
            scope.$parent.removeIteration(scope.getQuestionSetCode(), scope.getIteration(), scope.getRepetitionMap())


        scope.$watch 'ngCondition', () ->
            scope.$root.$broadcast('CONDITION')