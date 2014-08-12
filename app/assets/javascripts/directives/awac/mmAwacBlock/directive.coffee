define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacBlock", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngCondition: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacBlock/template.html"
    replace: true
    transclude: true
    link: (scope,element) ->
        directiveService.autoScopeImpl scope

        scope.$watch 'ngCondition', (n,o) ->
            if n != o
                scope.$root.$broadcast('CONDITION')