angular.module('app.directives').directive("mmAwacModalConfirmationExitForm", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html",
    controller: function($scope, modalService) {
      directiveService.autoScope;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRMATION_EXIT_FORM);
      };
      $scope["continue"] = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('NAV', arg);
        return $scope.close();
      };
      return $scope.save = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('SAVE_AND_NAV', arg);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});