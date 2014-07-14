angular
.module('app.controllers')
.controller "Form4Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB4"

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data

        # getAnswerByQuestionCode
        $scope.A = (code) ->
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    return qv
            return null

        # getUnitsByQuestionCode
        $scope.U = (code) ->
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

        # getOptionsByQuestionCode
        $scope.O = (code) ->
            codeListName = null;
            for q in $scope.o.questions
                if q.questionKey == code
                    codeListName = q.codeListName

            if codeListName == null
                console.error "impossible to find question by its code: " + code
                return null

            for cl in $scope.o.codeLists
                if cl.code == codeListName
                    return cl.codeLabels

            console.error "impossible to find codeList by its code: " + codeLabelName + " question code was: " + code

            return null

    $scope.save = () ->
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
