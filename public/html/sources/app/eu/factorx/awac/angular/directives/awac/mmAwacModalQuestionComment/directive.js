angular.module('app.directives').directive("mmAwacModalQuestionComment", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.QUESTION_COMMENT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        $scope.ngParams.save($scope.comment);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});