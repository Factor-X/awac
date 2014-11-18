#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalDocumentManager", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html"
    controller: ($scope, modalService, $location, $window) ->
        directiveService.autoScopeImpl $scope

        $scope.listDocuments = $scope.getParams().listDocuments

        console.log $scope.listDocuments

        $scope.show = false
        $scope.loc = null

        $scope.download = (storedFileId) ->
            url = '/awac/file/download/' + storedFileId
            $window.open(url);

        $scope.removeDoc = (storedFileId) ->
            if $scope.getParams().readyOnly != true
                delete $scope.listDocuments[storedFileId]
                $scope.getParams().wasEdited()


        $scope.close = ->
            modalService.close modalService.DOCUMENT_MANAGER


    link: (scope) ->

