angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-document-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerByQuestionCode = (code) ->
            if scope.ngObject
                for qv in scope.ngObject.answersSaveDTO.listAnswers
                    if qv.questionKey == code
                        return qv
            return null

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null


        scope.$watch 'ngCondition', () ->
        #if scope.getCondition() == false
        #  scope.getAnswerValue().value = null

