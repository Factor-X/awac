angular
.module('app.directives')
.directive "mmAwacLittleemitterMenu", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-littleemitter-menu.html"
    transclude: true
    replace: true
    link: ->
