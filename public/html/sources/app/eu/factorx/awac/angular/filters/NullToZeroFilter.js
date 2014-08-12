angular.module('app.filters').filter("nullToZero", function($sce, translationService) {
  return function(input) {
    if (input === void 0 || input === null) {
      return 0;
    } else {
      return parseFloat(input);
    }
  };
});