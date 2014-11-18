angular
.module('app.directives')
.directive "focusMe", ($timeout, $parse) ->
    restrict:'A'
    scope:
        focusMe:'='
    link: (scope, element, attrs) ->
        scope.$watch 'focusMe', ->
            if scope.focusMe == true
                element[0].focus()
        return
