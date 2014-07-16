angular
.module('app.directives')
.directive "mmAwacRealQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-real-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerByQuestionCode = (code) ->
            if scope.ngObject
                for qv in scope.ngObject.answersSaveDTO.listAnswers
                    if qv.questionKey == code
                        console.log qv
                        return qv
            return null
