angular
.module('app.directives')
.directive "mmAwacModalFieldText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html"
    replace: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.getInfo().isValid = !scope.getInfo().validationRegex?

        scope.$watch 'getInfo().field', (n, o) ->
            if n != o && scope.getInfo().validationRegex?
                scope.getInfo().isValid = scope.getInfo().field.match(scope.getInfo().validationRegex)
            return
