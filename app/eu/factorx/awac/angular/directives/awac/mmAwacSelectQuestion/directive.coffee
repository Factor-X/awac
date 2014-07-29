angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
    replace: true
    link: (scope) ->

        scope.getQuestionCode = ->
            return scope.$parent.getQuestionCode()

        scope.getRepetitionMap = ->
            return scope.$parent.getRepetitionMap()

        scope.getAnswerValue = () ->
            return scope.$parent.$parent.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.getOptionsByQuestionCode = () ->
            codeList = scope.$parent.$parent.$parent.getCodeList(scope.getQuestionCode())
            if codeList
                return codeList.codeLabels
            return null

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
          scope.getAnswerValue().wasEdited = true