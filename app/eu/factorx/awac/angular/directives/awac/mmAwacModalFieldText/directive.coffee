angular
.module('app.directives')
.directive "mmAwacModalFieldText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html"
    replace: true
    link: (scope,element) ->
        directiveService.autoScopeImpl scope
        scope.controlField = () ->
            scope.getInfo().isValid = true
            if scope.getInfo().validationRegex? && !scope.getInfo().field.match(scope.getInfo().validationRegex)
                scope.getInfo().isValid = false
            return
