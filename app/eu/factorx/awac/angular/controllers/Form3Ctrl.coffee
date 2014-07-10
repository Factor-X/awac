angular
.module('app.controllers')
.controller "Form3Ctrl", ($scope, downloadService, $http) ->
    $scope.formdId = 36
    $scope.periodId = 24
    $scope.scopeId = 27

    downloadService.getJson "answer/getByForm/" + $scope.formdId + "/" + $scope.periodId + "/" + $scope.scopeId, (data) ->
        $scope.o = data

        $scope.getAnswerByQuestionCode = (code) ->
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    return qv
            return null

        $scope.getUnitsByQuestionCode = (code) ->
            unitCategoryId = null;
            for q in $scope.o.questions
                if q.questionKey == code
                    unitCategoryId = q.unitCategoryId

            if unitCategoryId == null
                console.error "impossible to find question by its code: " + code
                return null

            for uc in $scope.o.unitCategories
                if uc.id == unitCategoryId
                    return uc.units

            console.error "impossible to find unit category by its id: " + unitCategoryId + " question code was: " + code

            return null

    $scope.$on 'SAVE', () ->
        promise = $http
            method: "POST"
            url: 'answer/save'
            headers:
                "Content-Type": "application/json"
            data: $scope.o.answersSaveDTO

        promise.success (data, status, headers, config) ->
            console.log "SAVE !"
            return

        promise.error (data, status, headers, config) ->
            console.log "ERROR : " + data.message
            return
