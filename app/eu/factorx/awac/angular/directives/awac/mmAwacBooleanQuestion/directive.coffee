angular
.module('app.directives')
.directive "mmAwacBooleanQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope:  directiveService.autoScope
        ngDataToCompare: '='
        ngIsAggregation:'='
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html"
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
        scope.$watch 'getAnswer().value', (o,n)->
            if ""+n != ""+o
                scope.$parent.edited()
