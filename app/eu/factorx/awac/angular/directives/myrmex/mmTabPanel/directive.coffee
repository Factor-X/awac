angular
.module('app.directives')
.directive "mmTabPanel", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngHeaderCode: "="
    templateUrl: "$/angular/templates/mm-tab-panel.html"
    transclude: true
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

