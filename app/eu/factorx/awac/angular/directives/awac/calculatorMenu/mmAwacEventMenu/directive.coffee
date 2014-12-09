angular
.module('app.directives')
.directive "mmAwacEventMenu", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-event-menu.html"
    transclude: true
    replace: true
    link: ->
