angular
.module('app.directives')
.directive "mmAwacQuestionSetTree", (RecursionHelper) ->
    restrict: "E"
    scope:
        ngModel: '='
    templateUrl: "$/angular/templates/mm-awac-question-set-tree.html"
    replace: false
    compile: (element) ->
        return RecursionHelper.compile(element, (scope, iElement, iAttrs, controller, transcludeFn) ->

        )