angular
.module('app.directives')
.directive "mmAwacDummySurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-dummy-survey.html"
    transclude: true
    replace: true
    controller: ($scope, downloadService,$http) ->

        $scope.formdId = 36
        $scope.periodId = 24
        $scope.scopeID = 27

        $scope.results=[]
        $scope.indicatorValue=0

        downloadService.getJson "answer/getByForm/"+$scope.formdId+"/"+$scope.periodId+"/"+$scope.scopeID, (data) ->
            $scope.o = data

            $scope.getByCode = (code) ->
                for qv in $scope.o.answersSaveDTO.listQuestionValueDTO
                    if qv.questionKey == code
                        return qv
                return null

        $scope.computeResult = () ->
          $scope.indicatorValue=0
          for result in $scope.results
            $scope.indicatorValue += parseInt result.value

        $scope.getValue = (code) ->
          for result in $scope.results
            if result.code == code
              return result
          result={code:code, value:0}
          $scope.results[$scope.results.length] = result
          return result

        $scope.save  = () ->
          #compute data
          listAnswers = []
          for result in $scope.results
            listAnswers[listAnswers.length] = {questionKey : result.code, value : result.value}
          promise = $http
            method: "POST"
            url: 'answer/save'
            headers:
              "Content-Type": "application/json"
            data:
              scopeId : $scope.scopeID
              periodId : $scope.periodId
              listAnswers : listAnswers

          promise.success (data, status, headers, config) ->
            console.log "SAVE !"
            return

          promise.error (data, status, headers, config) ->
            console.log "ERROR : "+data.message
            return


    link: (scope) ->
        directiveService.autoScopeImpl scope
