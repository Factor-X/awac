#
# TODO deprecated => to adapt to the new modal service
#
angular
.module('app.directives')
.directive "mmAwacModalQuestionComment", (directiveService) ->
    restrict: "E"
    scope: {}
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html"
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

        #change option of the modal
        $('#modalConfirmationExitForm').modal({
            backdrop: false
        })
        $('#modalConfirmationExitForm').modal('show')

        modalName = modalService.CONFIRMATION_EXIT_FORM
        $scope.show = false
        $scope.loc =null

        $scope.$on 'SHOW_MODAL_'+modalName,(event,args) ->
            if args.show
                $scope.display(args.params)
            else
                $scope.close()

            if args.comment?
                $scope.comment = angular.copy(args.comment)

        $scope.close= ->
            $scope.show = false
            modalService.hide "SHOW_MODAL_"+modalName

        $scope.display = (params)->
            $scope.show = true
            if params.loc != undefined
                $scope.loc = params.loc


        $scope.save = ->
            $scope.getComment() = comment


    link: (scope) ->

