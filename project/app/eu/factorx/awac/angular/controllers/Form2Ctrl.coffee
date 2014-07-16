angular
.module('app.controllers')
.controller "Form2Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB2"

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data


        $scope.addNewAnswer = (code) ->
            answers = $scope.A(code)

            maxRepetitionIndex = 0

            if answers
                for a in answers
                    if a.repetitionIndex > maxRepetitionIndex
                        maxRepetitionIndex = a.repetitionIndex

            $scope.o.answersSaveDTO.listAnswers.push {
                __type: "eu.factorx.awac.dto.awac.shared.AnswerLine"
                questionKey: code
                repetitionIndex: maxRepetitionIndex + 1
                unitId: null
                value: null
            }

        $scope.removeAnwser = (v) ->
            for a in $scope.o.answersSaveDTO.listAnswers
                if a.repetitionIndex == v.repetitionIndex and a.questionKey == v.questionKey
                    idx = $scope.o.answersSaveDTO.listAnswers.indexOf(a)
                    $scope.o.answersSaveDTO.listAnswers.splice(idx, 1)
                    console.log $scope.o.answersSaveDTO.listAnswers
                    return

        $scope.getRepetitionIndicesFor = (codes) ->

            res = {}

            repetitionIndices = []

            for c in codes
                answers = $scope.A(c)
                res[c] = _(answers).sortBy (a) -> [a.repetitionIndex]

            n = -1
            for k,v of res
                if n == -1
                    n = v.length
                else
                    if n != v.length
                        console.error 'For codes ' + codes + ' not the same number of answers'
                        return null

            for k,v of res
                console.log k
                n = v[0].repetitionIndex
                repetitionIndices[repetitionIndices.length] = n
                for ck, cv in codes
                    if cv.repetitionIndex != n
                        console.error 'repetitionIndices do not match ! for codes ' + codes
                        return null

            return repetitionIndices






        $scope.getByCodeAndRepetitionIndex = (code, ri) ->
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    if qv.repetitionIndex == ri
                        return qv
            return null

        # getAnswerByQuestionCode
        $scope.A = (code) ->
            isRepetition = false
            res = []
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    if qv.repetitionIndex != null
                        isRepetition = true
                    res[res.length] = qv

            if isRepetition
                return res
            else
                return res[0]

        # getUnitsByQuestionCode
        $scope.U = (code) ->
            unitCategoryId = null;
            for q in $scope.o.questions
                if q.questionKey == code
                    unitCategoryId = q.unitCategoryId

            if unitCategoryId == null
                # console.error "impossible to find question by its code: " + code
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
                # console.error "impossible to find question by its code: " + code
                return null

            for cl in $scope.o.codeLists
                if cl.code == codeListName
                    return cl.codeLabels

            console.error "impossible to find codeList by its code: " + codeLabelName + " question code was: " + code

            return null

    $scope.$on 'save', () ->
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

    return