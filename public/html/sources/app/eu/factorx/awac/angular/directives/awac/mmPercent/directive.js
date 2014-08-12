angular.module('app.directives').directive("percent", function($filter) {
  var f, p;
  p = function(viewValue) {
    return $filter("number")(viewValue / 100, 6);
  };
  f = function(modelValue) {
    return $filter("number")(parseFloat(modelValue) * 100, 2);
  };
  return {
    require: "ngModel",
    link: function(scope, ele, attr, ctrl) {
      ctrl.$parsers.unshift(p);
      ctrl.$formatters.unshift(f);
      return;
    }
  };
});