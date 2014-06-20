angular
.module('app.directives')
.directive "mmDoubleWithUnit", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngLabelCode: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-double-with-unit.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope