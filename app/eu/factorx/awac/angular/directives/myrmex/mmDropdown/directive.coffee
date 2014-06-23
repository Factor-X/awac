angular
.module('app.directives')
.directive "mmDropdown", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngOptions: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-dropdown.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope