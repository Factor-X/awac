angular
.module('app.components')
.directive "mmDouble", ->
    restrict: "E"
    scope:
        labelCode: "@"
        ngModel: "="
    templateUrl: "$/angular/templates/mm-double.html"
