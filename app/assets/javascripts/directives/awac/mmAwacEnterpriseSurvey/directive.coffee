define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacEnterpriseSurvey", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngModel: '='
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacEnterpriseSurvey/template.html"
    transclude: true
    replace: true
    controller: 'MainCtrl'
    link: (scope) ->
        directiveService.autoScopeImpl scope