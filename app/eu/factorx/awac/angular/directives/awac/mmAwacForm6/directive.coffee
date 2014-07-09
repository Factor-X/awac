angular
.module('app.directives')
.directive "mmAwacForm6", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form6.html"
    transclude: true
    replace: true
    controller: 'Form6Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
