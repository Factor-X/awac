angular
.module('app.directives')
.directive "mmAwacForm3", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form3.html"
    transclude: true
    replace: true
    controller: 'FormCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
