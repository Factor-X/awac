angular
.module('app')
.directive "mmDoubleWithUnit", ->
    restrict: "E"
    scope:
        labelCode: "@"
        ngModel: "="
    templateUrl: "$/angular/templates/mm-double-with-unit.html"
