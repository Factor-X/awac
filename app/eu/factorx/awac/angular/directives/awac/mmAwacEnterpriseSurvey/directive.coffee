angular
.module('app.directives')
.directive "mmAwacEnterpriseSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-enterprise-survey.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
