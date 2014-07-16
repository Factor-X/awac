angular
.module('app.directives')
.directive "mmAwacRealWithUnitQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.getAnswerByQuestionCode = (code) ->
            if scope.ngObject
                for qv in scope.ngObject.answersSaveDTO.listAnswers
                    if qv.questionKey == code
                        return qv
            return null

        scope.getUnitsByQuestionCode = (code) ->
            if scope.ngObject
                unitCategoryId = null;
                for q in scope.ngObject.questions
                    if q.questionKey == code
                        unitCategoryId = q.unitCategoryId

                if unitCategoryId == null
                    console.error "impossible to find question by its code: " + code
                    return null

                for uc in scope.ngObject.unitCategories
                    if uc.id == unitCategoryId
                        return uc.units

                console.error "impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code

            return null