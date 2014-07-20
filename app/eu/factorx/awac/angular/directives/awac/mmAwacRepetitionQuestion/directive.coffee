angular
.module('app.directives')
.directive "mmAwacRepetitionQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionSetCode: '='
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getQuestionSet = () ->
          return scope.$parent.getQuestionSet(scope.ngQuestionSetCode)
        ###

        scope.getAnswerByQuestionCode = (code) ->
            if scope.ngObject
                for qv in scope.ngObject.answersSaveDTO.listAnswers
                    if qv.questionKey == code
                        return qv
            return null


        scope.addNewAnswer = () ->
            if scope.ngObject

                for q in scope.getQuestionsToAdd().split(',')
                    answers = scope.getAnswerByQuestionCode(q)

                    console.log answers

                    maxRepetitionIndex = 0

                    if answers
                        for a in answers
                            if a.repetitionIndex > maxRepetitionIndex
                                maxRepetitionIndex = a.repetitionIndex

                    scope.ngObject.answersSaveDTO.listAnswers[scope.ngObject.answersSaveDTO.listAnswers.length] =
                        __type: "eu.factorx.awac.dto.awac.shared.AnswerLine"
                        questionKey: q
                        repetitionIndex: maxRepetitionIndex + 1
                        unitId: null
                        value: null

        scope.removeAnwser = (v) ->
            for a in scope.ngObject.answersSaveDTO.listAnswers
                if a.repetitionIndex == v.repetitionIndex and a.questionKey == v.questionKey
                    idx = scope.ngObject.answersSaveDTO.listAnswers.indexOf(a)
                    scope.ngObject.answersSaveDTO.listAnswers.splice(idx, 1)
                    console.log scope.ngObject.answersSaveDTO.listAnswers
                    return
        window.S = scope
        ###