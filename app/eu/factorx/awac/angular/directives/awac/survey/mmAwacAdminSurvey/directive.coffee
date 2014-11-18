angular
.module('app.directives')
.directive "mmAwacAdminSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-admin-survey.html"
    transclude: true
    replace: true
    controller: 'MainCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
