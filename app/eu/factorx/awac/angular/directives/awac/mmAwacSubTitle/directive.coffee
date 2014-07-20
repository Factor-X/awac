angular
.module('app.directives')
.directive "mmAwacSubTitle", () ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-sub-title.html"
    replace: true
    transclude: true
    link: (scope) ->
