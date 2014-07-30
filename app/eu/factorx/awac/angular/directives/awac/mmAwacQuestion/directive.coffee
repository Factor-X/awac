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
    link: (scope) ->
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
                else if answerType == 'STRING'
                    return "mmAwacStringQuestion"+toCompare
                else if answerType == 'VALUE_SELECTION'
                    return "mmAwacSelectQuestion"+toCompare
                else if answerType == 'DOCUMENT'
                    return "mmAwacDocumentQuestion"+toCompare

        scope.getAnswerValue = () ->
            return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap())

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.$watch 'ngCondition', () ->
            if scope.getCondition() == false
                scope.getAnswerValue().value = null
            else if scope.$parent.loading == false && scope.getAnswerValue().value == null
                scope.getAnswerValue().value = scope.$parent.getQuestion(scope.getQuestionCode()).defaultValue
                if scope.$parent.getUnitCategories(scope.getQuestionCode())!=null
                    scope.getAnswerValue().unitId = scope.$parent.getUnitCategories(scope.getQuestionCode()).mainUnitId

        scope.displayOldDatas = ->
            if scope.$parent.dataToCompare == null
                return false
            return true

        #
        # called when the user change the value of the field
        #
        scope.edited = ->
            scope.getAnswerValue().wasEdited = true
