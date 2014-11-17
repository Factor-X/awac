angular
.module('app.directives')
.directive "mmAwacVerificationSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-verification-survey.html"
    transclude: true
    replace: true
    controller: 'MainCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
