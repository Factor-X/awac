angular
.module('app.directives')
.directive "mmNotImplemented", (directiveService) ->
    restrict: "A"
    scope: {}
    link: (scope, elem, attrs) ->
        elem.css('opacity', '0.25');
        elem.bind 'click', (e) ->
            e.preventDefault()
            e.stopPropagation()
            return false
