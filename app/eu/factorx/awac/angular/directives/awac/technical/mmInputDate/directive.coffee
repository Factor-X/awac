angular
.module('app.directives')
.directive "inputDate", ($filter) ->
    restrict: 'A'
    require: "ngModel"
    link: (scope, element, attrs, modelCtrl) ->

        modelCtrl.$formatters.unshift (modelValue) ->
            return $filter('date')(modelValue, 'yyyy-MM-dd hh:mm a')
