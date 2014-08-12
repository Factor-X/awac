angular.module('app.directives').directive("mmAwacModalDocumentManager", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html",
    controller: function($scope, modalService, $http, $location, $window) {
      var modalName;
      $scope.listDocuments = [];
      $('#modalDocumentManager').modal({
        backdrop: false
      });
      $('#modalDocumentManager').modal('show');
      modalName = modalService.DOCUMENT_MANAGER;
      $scope.show = false;
      $scope.loc = null;
      $scope.$on('SHOW_MODAL_' + modalName, function(event, args) {
        if (args.show) {
          $scope.display();
        } else {
          $scope.close();
        }
        console.log("args");
        console.log(args);
        $scope.listDocuments = args.params['listDocuments'];
        console.log("listDocuments");
        return console.log($scope.listDocuments);
      });
      $scope.display = function() {
        return $scope.show = true;
      };
      $scope.close = function() {
        $scope.show = false;
        return modalService.hide("SHOW_MODAL_" + modalName);
      };
      $scope.download = function(storedFileId) {
        var url;
        url = $location.absUrl().replace(/#.*$/, "") + 'file/download/' + storedFileId;
        return $window.open(url);
      };
      return $scope.removeDoc = function(storedFileId) {};
    },
    link: function(scope) {}
  };
});