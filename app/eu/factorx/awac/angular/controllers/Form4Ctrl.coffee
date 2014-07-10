angular
.module('app.controllers')
.controller "Form4Ctrl", ($scope, downloadService, $http) ->
    $scope.formdId = 36
    $scope.periodId = 24
    $scope.scopeId = 27

    downloadService.getJson "answer/getByForm/" + $scope.formdId + "/" + $scope.periodId + "/" + $scope.scopeId, (data) ->
        $scope.o = data

        $scope.getByCode = (code) ->
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    return qv
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
