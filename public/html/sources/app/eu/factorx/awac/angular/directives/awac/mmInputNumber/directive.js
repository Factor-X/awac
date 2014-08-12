angular.module('app.directives').directive("numbersOnly", function($filter, translationService) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      var f, nbDecimal, p;
      nbDecimal = 2;
      if (attrs.numbersOnly === "integer") {
        nbDecimal = 0;
      }
      p = function(viewValue) {
        var value;
        value = viewValue.toLocaleString();
        return $filter("number")(value, 6);
      };
      f = function(modelValue) {
        return $filter("number")(parseFloat(modelValue), nbDecimal);
      };
      modelCtrl.$parsers.unshift(p);
      modelCtrl.$formatters.unshift(f);
      return modelCtrl.$parsers.push(function(inputValue) {
        var transformedInput;
        if (inputValue === undefined) {
          return "";
        }
        scope.errorMessage = "";
        if (attrs.numbersOnly === "integer") {
          scope.regex = /[^0-9]/g;
          scope.errorMessage = translationService.get('FIELD_INTEGER_ERROR_MESSAGE');
        } else {
          scope.regex = /[^0-9,. ]/g;
          scope.errorMessage = translationService.get('FIELD_DECIMAL_ERROR_MESSAGE');
        }
        if (inputValue != null) {
          transformedInput = inputValue.replace(scope.regex, "");
          if (transformedInput !== inputValue) {
            if ((scope.$parent != null) && (scope.$parent.setErrorMessage != null)) {
              scope.$parent.setErrorMessage(scope.errorMessage);
            }
            if (transformedInput === "") {
              transformedInput = null;
            }
            modelCtrl.$setViewValue(transformedInput);
            modelCtrl.$render();
          }
          return transformedInput;
        }
      });
    }
  };
});