angular.module('app.directives').directive("mmAwacModalLoading", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html",
    controller: function($scope, modalService) {
      return $scope.close = function() {
        return modalService.close(modalService.LOADING);
      };
    },
    link: function(scope) {}
  };
});