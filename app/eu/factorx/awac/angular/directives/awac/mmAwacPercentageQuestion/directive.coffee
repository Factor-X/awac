angular
.module('app.directives')
.directive "mmAwacPercentageQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html"
    replace: true
    link: (scope) ->

        scope.getQuestionCode = ->
            return scope.$parent.getQuestionCode()

        scope.getRepetitionMap = ->
            return scope.$parent.getRepetitionMap()

        scope.getAnswerValue = () ->
            return scope.$parent.$parent.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
          scope.getAnswerValue().wasEdited = true

