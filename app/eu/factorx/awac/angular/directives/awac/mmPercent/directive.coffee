angular
.module('app.directives')
.directive "percent", ($filter) ->
    p = (viewValue) ->
        return $filter("number") viewValue / 100,6
    f = (modelValue) ->
        $filter("number") parseFloat(modelValue) * 100, 2

    require: "ngModel"
    link: (scope, ele, attr, ctrl) ->
        ctrl.$parsers.unshift p
        ctrl.$formatters.unshift f
        return
