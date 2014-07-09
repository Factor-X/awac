angular
.module('app.directives')
.directive "mmAwacForm__N__", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-form__N__.html"
    transclude: true
    replace: true
    controller: 'Form__N__Ctrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope
