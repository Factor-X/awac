angular
.module('app.directives')
.directive "mmTab", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngHeaderCode: '='
        ngActive: '='
    templateUrl: "$/angular/templates/mm-tab.html"
    transclude: true
    replace: true
    link: (scope, element) ->
        directiveService.autoScopeImpl scope

        scope.$select = () ->
            tabs = element.parent().children()
            angular.forEach tabs, (tab) ->
                s = angular.element(tab).children().scope()
                s.active = scope == s
            return false