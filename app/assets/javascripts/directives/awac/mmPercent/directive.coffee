define ["../../module"], (directives) ->
  "use strict"
  directives.directive "percent", ($filter) ->
    p = (viewValue) ->
        m = viewValue.match(/^(\d+)\/(\d+)/)
        return $filter("number")(parseInt(m[1]) / parseInt(m[2]), 2)  if m?
        $filter("number") parseFloat(viewValue) / 100, 2

    f = (modelValue) ->
        $filter("number") parseFloat(modelValue) * 100, 2

    require: "ngModel"
    link: (scope, ele, attr, ctrl) ->
        ctrl.$parsers.unshift p
        ctrl.$formatters.unshift f
        return
