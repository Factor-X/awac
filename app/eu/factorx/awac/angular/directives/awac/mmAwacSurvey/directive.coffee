angular
.module('app.directives')
.directive "mmAwacSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-survey.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
