#
# TODO deprecated => to adapt to the new modal service
#
define ["../../module"], (directives) ->
  "use strict"
  directives.directive "mmAwacModalDocumentManager", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacModalDocumentManager/template.html"
    controller: ($scope, modalService,$http,$location,$window) ->

        #change option of the modal
        $('#modalDocumentManager').modal({
            backdrop: false
        })
        $('#modalDocumentManager').modal('show')

        modalName = modalService.DOCUMENT_MANAGER
        $scope.show = false
        $scope.loc =null
        $scope.listDocuments = []

        $scope.$on 'SHOW_MODAL_'+modalName,(event,args) ->
            if args.show
                $scope.display()
            else
                $scope.close()

            console.log "args"
            console.log args

            $scope.listDocuments = args.params['listDocuments']

            console.log "listDocuments"
            console.log $scope.listDocuments


        $scope.display = ()->
            $scope.show = true

        $scope.close= ->
            $scope.show = false
            modalService.hide "SHOW_MODAL_"+modalName

        $scope.download = (storedFileId) ->
            url = $location.absUrl().replace(/#.*$/, "")+'file/download/'+storedFileId
            $window.open(url);

        $scope.removeDoc = (storedFileId) ->
            #TODO


    link: (scope) ->

