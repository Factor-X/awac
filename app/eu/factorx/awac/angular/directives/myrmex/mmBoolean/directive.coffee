angular
.module('app.directives')
.directive "mmBoolean", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngRequired: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-boolean.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope


