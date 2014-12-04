angular
.module('app.directives')
.directive "mmAdminTranslationsNavigationBar", () ->
    restrict: "E"
    scope:
        ngModel: '='
    templateUrl: "$/angular/templates/mm-admin-translations-navigation-bar.html"
    replace:true
    controller: ($scope) ->

