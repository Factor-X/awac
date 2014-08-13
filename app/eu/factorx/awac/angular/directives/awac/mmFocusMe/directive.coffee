angular
.module('app.directives')
.directive "focusMe", ($timeout, $parse) ->

    #scope: true,   // optionally create a child scope
    link: (scope, element, attrs) ->
        $timeout ->
            element[0].focus()
            return
        return
