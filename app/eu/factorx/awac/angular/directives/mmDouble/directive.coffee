angular
.module('app.directives')
.directive "mmDouble", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabelCode: "="
        ngRequired: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-double.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope
