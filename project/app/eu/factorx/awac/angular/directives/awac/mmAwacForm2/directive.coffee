angular
.module('app.directives')
.directive "mmAwacForm2", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form2.html"
    transclude: true
    replace: true
    controller: 'Form2Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
