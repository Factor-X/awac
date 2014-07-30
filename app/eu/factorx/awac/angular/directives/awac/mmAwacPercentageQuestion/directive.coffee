angular
.module('app.directives')
.directive "mmAwacPercentageQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope:  directiveService.autoScope
        ngDataToCompare: '='
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getQuestionCode = ->
            return scope.$parent.getQuestionCode()

        scope.getRepetitionMap = ->
            return scope.$parent.getRepetitionMap()

        scope.getAnswerValue = () ->
            if scope.getDataToCompare() == true
                return scope.$parent.$parent.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap())
            else
                return scope.$parent.$parent.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
          scope.getAnswerValue().wasEdited = true

