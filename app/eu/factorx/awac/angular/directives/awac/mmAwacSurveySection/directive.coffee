angular
.module('app.directives')
.directive "mmAwacSurveySection", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngHeader: "="
    templateUrl: "$/angular/templates/mm-awac-survey-section.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
