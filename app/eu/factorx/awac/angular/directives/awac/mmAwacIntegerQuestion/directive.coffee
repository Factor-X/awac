angular
.module('app.directives')
.directive "mmAwacIntegerQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngDataToCompare: '='
    templateUrl: "$/angular/templates/mm-awac-integer-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        #
        # get the question code :
        # call the getQuestionCode from the parent
        #
        scope.getQuestionCode = ->
            return scope.$parent.getQuestionCode()

        #
        # get the answer :
        # call the getAnswerOrCreate from the parent or the
        # getAnswerToCompare if the question is a dataToCompare
        #
        scope.getAnswer = () ->
            return scope.$parent.getAnswer(scope.getDataToCompare())

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
            scope.$parent.edited()

