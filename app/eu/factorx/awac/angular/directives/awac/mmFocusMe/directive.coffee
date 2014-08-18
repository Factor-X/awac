angular
.module('app.directives')
.directive "focusMe", ($timeout, $parse) ->
    restrict:'A'
    link: (scope, element, attrs) ->

        scope.$watch 'attrs.focusMe', ->
            if attrs.focusMe == 'true'
                $timeout ->
                    element[0].focus()
                    return
        return
