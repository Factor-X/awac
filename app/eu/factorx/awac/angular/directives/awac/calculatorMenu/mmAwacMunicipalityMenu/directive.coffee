angular
.module('app.directives')
.directive "mmAwacMunicipalityMenu", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-municipality-menu.html"
    transclude: true
    replace: true
    link: ->
