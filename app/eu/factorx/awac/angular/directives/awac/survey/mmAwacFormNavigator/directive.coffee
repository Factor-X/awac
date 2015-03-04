angular
.module('app.directives')
.directive "mmAwacFormNavigator", (directiveService) ->
    restrict: "E"
    templateUrl: "$/angular/templates/mm-awac-form-navigator.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope
