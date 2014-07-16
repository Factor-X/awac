angular
.module('app.directives')
.directive "mmAwacSelectQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-select-question.html"
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

        scope.getOptionsByQuestionCode = (code) ->
            if scope.ngObject
                codeListName = null;
                for q in scope.ngObject.questions
                    if q.questionKey == code
                        codeListName = q.codeListName

                if codeListName == null
                    console.error "impossible to find question by its code: " + code
                    return null

                for cl in scope.ngObject.codeLists
                    if cl.code == codeListName
                        return cl.codeLabels

                console.error "impossible to find codeList by its code: " + codeLabelName + " question code was: " + code

            return null
