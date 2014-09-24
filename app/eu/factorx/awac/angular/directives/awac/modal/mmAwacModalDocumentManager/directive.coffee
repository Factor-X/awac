#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalDocumentManager", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html"
    controller: ($scope, modalService, $location, $window) ->
        $scope.listDocuments = []


        #change option of the modal
        $('#modalDocumentManager').modal({
            backdrop: false
        })
        $('#modalDocumentManager').modal('show')

        modalName = modalService.DOCUMENT_MANAGER
        $scope.show = false
        $scope.loc = null

        $scope.$on 'SHOW_MODAL_' + modalName, (event, args) ->
            if args.show
                $scope.display()
            else
                $scope.close()

            #console.log "args"
            #console.log args

            $scope.listDocuments = args.params['listDocuments']

        #console.log "listDocuments"
        #console.log $scope.listDocuments


        $scope.display = ()->
            $scope.show = true

        $scope.close = ->
            $scope.show = false
            modalService.hide "SHOW_MODAL_" + modalName

        $scope.download = (storedFileId) ->
            url = '/awac/file/download/' + storedFileId
            $window.open(url);

        $scope.removeDoc = (storedFileId) ->
            #TODO


    link: (scope) ->

