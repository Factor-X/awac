angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope:  directiveService.autoScope
        ngDataToCompare: '='
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
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
        # call the getAnswerOrCreate parent method or the
        # getAnswerToCompare if the question is a dataToCompare
        #
        scope.getAnswer = () ->
            return scope.$parent.getAnswer(scope.getDataToCompare())

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
            scope.$parent.edited()

        #
        # return the list of options that can be choose
        # call the getCodeList parent method
        #
        scope.getOptions = ->
            return scope.$parent.getCodeList().codeLabels