angular
.module('app.directives')
.directive "mmAwacIntegerQuestion", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngObject:'='
        ngQuestionCode:'='
    templateUrl: "$/angular/templates/mm-awac-integer-question.html"
    replace:true
    link: (scope) ->
        directiveService.autoScopeImpl scope

        
