angular
.module('app.directives')
.directive "mmAwacEnterpriseMenu", () ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-enterprise-menu.html"
    transclude: true
    replace: true
    link: ->
