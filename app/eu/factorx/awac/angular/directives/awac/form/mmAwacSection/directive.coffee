angular
.module('app.directives')
.directive "mmAwacSection", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngTitleCode: '='
        ngMode: '='
    templateUrl: "$/angular/templates/mm-awac-section.html"
    replace: true
    transclude: true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        scope.decorateMode = (v) ->
            if v
                return 'element_' + v
            else
                return 'element_table'
