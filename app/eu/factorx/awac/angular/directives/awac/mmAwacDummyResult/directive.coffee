angular
.module('app.directives')
.directive "mmAwacDummyResult", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-dummy-result.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
