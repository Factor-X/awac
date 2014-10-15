angular
.module('app.directives')
.directive "ngEnter", () ->
    return (scope, element, attrs) ->
        element.bind "keydown keypress", (event) ->
            if event.which == 13 #&& event.target.tagName != 'TEXTAREA'
                scope.$apply () ->
                    scope.$eval(attrs.ngEnter)
                event.preventDefault()

