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

        scope.getKeys = () ->
            return [e.key for e in scope.ngOptions]

        scope.getText = (key) ->
            for e in scope.ngOptions
                if e.key == key
                    return e.value
            return null