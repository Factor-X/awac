angular
.module('app.directives')
.directive "mmAwacForm4", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form4.html"
    transclude: true
    replace: true
    controller: 'FormCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
