angular
.module('app.directives')
.directive "mmDouble", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngRequired: "="
        ngModel: "="
    templateUrl: "$/angular/templates/mm-double.html"
    link: (scope) ->
        directiveService.autoScopeImpl scope


.directive 'mmValidInteger', () ->
    require: 'ngModel'
    link: (scope, elm, attrs, ctrl) ->
        ctrl.$parsers.unshift (viewValue) ->
            r = /^\-?\d+$/
            if r.test(viewValue)
                ctrl.$setValidity('integer', true)
                return viewValue
            else
                ctrl.$setValidity('integer', false)
                return

.directive 'mmValidDouble', () ->
    require: 'ngModel'
    link: (scope, elm, attrs, ctrl) ->
        ctrl.$parsers.unshift (viewValue) ->
            r = /^\-?\d+((\.|\,)\d+)?$/
            if (r.test(viewValue))
                ctrl.$setValidity('double', true)
                return viewValue
            else
                ctrl.$setValidity('double', false)
                return


