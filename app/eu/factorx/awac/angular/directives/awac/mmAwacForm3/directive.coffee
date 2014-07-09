angular
.module('app.directives')
.directive "mmAwacForm3", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form3.html"
    transclude: true
    replace: true
    controller: 'Form3Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
