define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacSubTitle", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacSubTitle/template.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null
