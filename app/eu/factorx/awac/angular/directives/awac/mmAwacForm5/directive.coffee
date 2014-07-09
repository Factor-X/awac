angular
.module('app.directives')
.directive "mmAwacForm5", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form5.html"
    transclude: true
    replace: true
    controller: 'Form5Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
