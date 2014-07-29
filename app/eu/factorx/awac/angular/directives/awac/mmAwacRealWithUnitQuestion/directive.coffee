angular
.module('app.directives')
.directive "mmAwacRealWithUnitQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html"
    replace: true
    link: (scope) ->

        scope.getQuestionCode = ->
            return scope.$parent.getQuestionCode()

        scope.getRepetitionMap = ->
            return scope.$parent.getRepetitionMap()

        scope.getAnswerValue = () ->
            return scope.$parent.$parent.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.getUnitsByQuestionCode = () ->
            result = scope.$parent.$parent.$parent.getUnitCategories(scope.getQuestionCode())
            if result
                return result.units
            return null

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
          scope.getAnswerValue().wasEdited = true