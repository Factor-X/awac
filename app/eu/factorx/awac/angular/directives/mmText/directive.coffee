angular
.module('app.directives')
.directive "mmText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabelCode: "="
        ngRequired: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-text.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope
