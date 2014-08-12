define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacSection", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngTitleCode: '='
        ngMode: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacSection/template.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.decorateMode = (v) ->
            if v
                return 'element_' + v
            else
                return 'element_table'
