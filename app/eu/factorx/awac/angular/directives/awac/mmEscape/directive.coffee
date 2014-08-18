angular
.module('app.directives')
.directive "ngEscape", () ->
    return (scope, element, attrs) ->
        element.bind "keydown keypress", (event) ->
            if event.which == 27
                scope.$apply () ->
                    scope.$eval(attrs.ngEscape)
                event.preventDefault()

