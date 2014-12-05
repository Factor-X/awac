angular
.module('app.directives')
.directive "mmAwacHouseholdMenu", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-household-menu.html"
    transclude: true
    replace: true
    link: ->
