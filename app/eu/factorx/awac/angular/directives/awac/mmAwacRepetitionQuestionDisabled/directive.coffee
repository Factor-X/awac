angular
.module('app.directives')
.directive "mmAwacRepetitionQuestionDisabled", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngIteration: '='
        ngRepetitionMap: '='
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-awac-repetition-question-disabled.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getQuestionSet = () ->
            return scope.$parent.getQuestionSet(scope.getQuestionCode())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.removeAnwser = () ->
            scope.$parent.removeIteration(scope.getQuestionCode(), scope.getIteration(), scope.getRepetitionMap())


        scope.$watch 'ngCondition', () ->
            scope.$root.$broadcast('CONDITION')

        scope.addIteration = ->
            scope.$parent.addIteration(scope.getQuestionCode(),scope.getRepetitionMap())

        scope.getRepetitionMapByQuestionSet = ->
            scope.$parent.getRepetitionMapByQuestionSet(scope.getQuestionCode(),scope.getRepetitionMap())