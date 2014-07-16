angular
.module('app.directives')
.directive "mmAwacSection", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngTitleCode: '='
    templateUrl: "$/angular/templates/mm-awac-section.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
