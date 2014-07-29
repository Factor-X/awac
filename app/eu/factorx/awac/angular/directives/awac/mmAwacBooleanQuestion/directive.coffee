angular
.module('app.directives')
.directive "mmAwacBooleanQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html"
    replace: true
    link: (scope) ->

        scope.getQuestionCode = ->
            console.log scope.$parent
            return scope.$parent.getQuestionCode()

        scope.getCondition = ->
            return scope.$parent.getCondition()

        scope.getRepetitionMap = ->
            return scope.$parent.getRepetitionMap()

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.$watch 'ngCondition', () ->
            if scope.getCondition() == false
                scope.getAnswerValue().value = null
            else if scope.$parent.$parent.$parent.loading == false && scope.getAnswerValue().value==null
                scope.getAnswerValue().value = scope.$parent.$parent.$parent.getQuestion(scope.getQuestionCode()).defaultValue

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
          scope.getAnswerValue().wasEdited = true
