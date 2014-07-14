angular
.module('app.directives')
.directive "mmAwacForm7", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form7.html"
    transclude: true
    replace: true
    controller: 'Form7Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
