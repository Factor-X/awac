angular
.module('app.directives')
.directive "mmUnit", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngRequired: "="
        ngModel: "="
        ngOptions: "="
    templateUrl: "$/angular/templates/mm-unit.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.isSelected = (key) ->
            console.log "TEST !"
            console.log scope.ngModel.unit
            console.log key
            scope.ngModel.unit == key