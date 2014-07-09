angular
.module('app.directives')
.directive "mmAwacForm1", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form1.html"
    transclude: true
    replace: true
    controller: 'Form1Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
