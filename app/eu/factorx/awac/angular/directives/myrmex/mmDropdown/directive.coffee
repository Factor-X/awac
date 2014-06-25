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

        scope.select = (o) ->
            scope.sel = o
            scope.ngModel.value = o.key

        scope.getValue = () ->
            for o in scope.ngOptions
                if o.key == scope.ngModel.value
                    return o.value
            return null

        scope.isSelected = (key) ->
            scope.ngModel.value == key