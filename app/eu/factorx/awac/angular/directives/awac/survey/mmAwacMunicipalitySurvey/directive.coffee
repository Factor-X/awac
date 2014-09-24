angular
.module('app.directives')
.directive "mmAwacMunicipalitySurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-municipality-survey.html"
    transclude: true
    replace: true
    controller: 'MainCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
