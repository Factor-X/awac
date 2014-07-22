angular
.module('app.directives')
.directive "mmAwacSubSubTitle", (directiveService, translationService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
    templateUrl: "$/angular/templates/mm-awac-sub-sub-title.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.hasDescription = () ->
            return translationService.get(scope.getQuestionCode() + '_DESC') != null
