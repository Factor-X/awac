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
            scope.id = generateId.generate()
            scope.idHtag = '#' + scope.id

        post: (scope) ->
            directiveService.autoScopeImpl scope

            scope.result = null

            scope.$watch 'result', ->
                scope.resultFormated = $filter('date')(scope.result, 'yyyy-MM-dd hh:mm a')
                if scope.getAnswer()?
                    if scope.result?
                        scope.getAnswer().value = scope.result.getTime()
                    else
                        scope.getAnswer().value = null

            #
            # called when the user change the value of the field
            #
            if scope.getDataToCompare() == false && scope.getIsAggregation() == false
                scope.$watch 'getAnswer().value', (o, n)->
                    if "" + n != "" + o
                        scope.$parent.edited()

            scope.setErrorMessage = (errorMessage) ->
                scope.$parent.setErrorMessage(errorMessage)

            #
            # get the answer :
            # call the getAnswerOrCreate from the parent or the
            # getAnswerToCompare if the question is a dataToCompare
            #
            scope.getAnswer = () ->
                return scope.$parent.getAnswer(scope.getDataToCompare())

            scope.getDisabled = ->
                return scope.$parent.isDisabled()

            #
            # get the question code :
            # call the getQuestionCode from the parent
            #
            scope.getQuestionCode = ->
                return scope.$parent.getQuestionCode()
