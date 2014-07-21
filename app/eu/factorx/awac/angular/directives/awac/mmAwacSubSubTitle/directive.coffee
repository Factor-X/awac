angular
.module('app.directives')
.directive "mmAwacSubSubTitle", () ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-sub-sub-title.html"
    replace: true
    transclude: true
    link: (scope) ->
