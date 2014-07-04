angular
.module('app.directives')
.directive "mmAwacDummySurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-dummy-survey.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
