angular
.module('app.directives')
.directive "mmAwacModalFieldText", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngInfo: '='
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.isValidationDefined = scope.getInfo().validationRegex? || scope.getInfo().validationFct?
        scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon
        scope.fieldType = if (scope.getInfo().fieldType?) then scope.getInfo().fieldType else "text"

        if !scope.getInfo().field?
            scope.getInfo().field=""

        if !scope.getInfo().isValid?
            scope.getInfo().isValid = !scope.isValidationDefined

        if scope.isValidationDefined
            scope.$watch 'getInfo().field', (n, o) ->
                if n != o
                    scope.isValid()

        scope.isValid = ->
            isValid = true
            if scope.getInfo().validationRegex?
                isValid = scope.getInfo().field.match(scope.getInfo().validationRegex)
            if scope.getInfo().validationFct?
                isValid = isValid && scope.getInfo().validationFct()
            scope.getInfo().isValid = isValid
            return

        scope.isValid()

        scope.logField = ->
            console.log scope.getInfo()
