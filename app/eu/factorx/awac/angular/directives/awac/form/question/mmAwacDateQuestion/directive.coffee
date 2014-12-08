angular
.module('app.directives')
.directive "mmAwacDateQuestion", (directiveService, translationService, generateId, $filter) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngDataToCompare: '='
        ngIsAggregation: '='
    templateUrl: "$/angular/templates/mm-awac-date-question.html"
    replace: true
    compile: ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

            scope.id = generateId.generate()
            scope.idHtag = '#' + scope.id

        post: (scope, $filter) ->
            directiveService.autoScopeImpl scope

            scope.result = new Date()

            scope.$watch 'result',(o,n) ->
                if o != n
                    if scope.getAnswer()?
                        scope.getAnswer().value = scope.result.getTime()

            scope.getDisabled = ->
                return scope.$parent.isDisabled()

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
            if scope.getDataToCompare() == false && scope.getIsAggregation() == false
                scope.$watch 'getAnswer().value', (o, n)->
                    if "" + n != "" + o
                        scope.$parent.edited()
                        scope.result.setTime(scope.getAnswer().value)

            scope.setErrorMessage = (errorMessage) ->
                scope.$parent.setErrorMessage(errorMessage)


