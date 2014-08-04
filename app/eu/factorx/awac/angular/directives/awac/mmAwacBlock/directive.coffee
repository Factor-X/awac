angular
.module('app.directives')
.directive "mmAwacBlock", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngCondition: '='
    templateUrl: "$/angular/templates/mm-awac-block.html"
    replace: true
    transclude: true
    link: (scope,element) ->
        directiveService.autoScopeImpl scope

        scope.$watch 'ngCondition', () ->
            scope.$root.$broadcast('CONDITION')