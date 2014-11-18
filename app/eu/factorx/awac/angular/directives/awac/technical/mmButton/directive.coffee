angular
.module('app.directives')
.directive "mmButton", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngCode: '='
        ngIcon: '='
    templateUrl: "$/angular/templates/mm-button.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
