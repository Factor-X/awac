angular
.module('app.directives')
.directive "mmAwacQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabelCode: "="
        ngReviewers: "="
    templateUrl: "$/angular/templates/mm-awac-question.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
