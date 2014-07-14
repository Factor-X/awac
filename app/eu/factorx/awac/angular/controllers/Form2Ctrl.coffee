angular
.module('app.controllers')
.controller "Form2Ctrl", ($scope, downloadService, $http) ->
    $scope.formIdentifier = "TAB2"

    downloadService.getJson "answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$parent.period + "/" + $scope.$parent.scopeId, (data) ->
        $scope.o = data

        $scope.getByCode = (code) ->
            for qv in $scope.o.answersSaveDTO.listAnswers
                if qv.questionKey == code
                    return qv
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