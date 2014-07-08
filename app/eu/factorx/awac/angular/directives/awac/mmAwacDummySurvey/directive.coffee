angular
.module('app.directives')
.directive "mmAwacDummySurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-dummy-survey.html"
    transclude: true
    replace: true
    controller: ($scope, downloadService) ->
        downloadService.getJson "answer/getByForm/36/24/27", (data) ->
            $scope.o = data

            $scope.getByCode = (code) ->
                for qv in $scope.o.answersSaveDTO.listQuestionValueDTO
                    if qv.questionKey == code
                        return qv
                return null
    link: (scope) ->
        directiveService.autoScopeImpl scope
