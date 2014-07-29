angular
.module('app.directives')
.directive "mmAwacModalLoading", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html"
    controller: ($scope, modalService) ->

            #change option of the modal
        $('#modalLoading').modal({
            backdrop: false
        })
        $('#modalLoading').modal('show')

        modalName = modalService.LOADING
        $scope.show = false
        $scope.loc =null

        $scope.$on 'SHOW_MODAL_'+modalName,(event,args) ->
            if args.show
                $scope.display()
            else
                $scope.close()

        $scope.display = ()->
            $scope.show = true

        $scope.close= ->
            $scope.show = false
            modalService.hide "SHOW_MODAL_"+modalName

    link: (scope) ->

