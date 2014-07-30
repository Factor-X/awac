angular
.module('app.directives')
.directive "mmAwacQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngRepetitionMap: '='
    templateUrl: "$/angular/templates/mm-awac-question.html"
    replace: true
    link: (scope,element) ->
        directiveService.autoScopeImpl scope

        scope.getTemplate = (dataToCompare)->

            toCompare = ""
            if  dataToCompare == true
                toCompare = "ToCompare"


            if scope.$parent.getQuestion(scope.getQuestionCode()) != null
                answerType = scope.$parent.getQuestion(scope.getQuestionCode()).answerType

                #call the directive by the type of the question
                if answerType == 'BOOLEAN'
                    return "mmAwacBooleanQuestion"+toCompare
                else if answerType == 'INTEGER'
                    return "mmAwacIntegerQuestion"+toCompare
                else if answerType == 'DOUBLE'
                    if scope.$parent.getQuestion(scope.getQuestionCode()).unitCategoryId != null || scope.$parent.getQuestion(scope.getQuestionCode()).unitCategoryId != undefined
                        return "mmAwacRealWithUnitQuestion"+toCompare
                    else
                        return "mmAwacRealQuestion"+toCompare
                else if answerType == 'PERCENTAGE'
                    return "mmAwacPercentageQuestion"+toCompare
                else if answerType == 'STRING'
                    return "mmAwacStringQuestion"+toCompare
                else if answerType == 'VALUE_SELECTION'
                    return "mmAwacSelectQuestion"+toCompare
                else if answerType == 'DOCUMENT'
                    return "mmAwacDocumentQuestion"+toCompare

        scope.getAnswerToCompare = ->
            return scope.$parent.getAnswerToCompare(scope.getQuestionCode(),scope.getRepetitionMap())

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null

        scope.displayOldDatas = ->
            if scope.$parent.dataToCompare == null
                return false
            return true

        scope.$watch 'ngCondition', () ->
            scope.$root.$broadcast('CONDITION')

        scope.$on 'CONDITION',(event,args) ->
            scope.testVisibility(element)

        scope.testVisibility = (elementToTest)->

            if elementToTest.hasClass('ng-hide') == true
                scope.getAnswerValue().hasValidCondition = false
                return false
            if elementToTest.parent()[0].tagName!='BODY'
                return scope.testVisibility(elementToTest.parent())

            scope.getAnswerValue().hasValidCondition = true
            return true

        scope.testVisibility(element)

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
            scope.getAnswerValue().wasEdited = true
