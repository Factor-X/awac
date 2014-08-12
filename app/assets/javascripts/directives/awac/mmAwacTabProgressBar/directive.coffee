define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacTabProgressBar", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngValue: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacTabProgressBar/template.html"
    replace: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.pg = null
        scope.color = null

        scope.$watch 'value', (v) ->
            if v < 0
                v = 0
            if v > 100
                v = 100

            if v >= 66
                scope.color = 'green'
            else
                if v >= 33
                    scope.color = 'orange'
                else
                    scope.color = 'red'

            scope.pg = v

