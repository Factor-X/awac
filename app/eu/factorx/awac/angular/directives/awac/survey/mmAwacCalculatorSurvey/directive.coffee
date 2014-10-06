angular
.module('app.directives')
.directive "mmAwacCalculatorSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-calculator-survey.html"
    transclude: true
    replace: true
    controller: 'MainCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
