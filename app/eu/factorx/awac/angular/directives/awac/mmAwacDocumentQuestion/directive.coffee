angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
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
