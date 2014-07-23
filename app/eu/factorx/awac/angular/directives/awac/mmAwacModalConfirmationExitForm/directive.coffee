angular
.module('app.directives')
.directive "mmAwacModalConfirmationExitForm", (directiveService) ->
    restrict: "E"
    scope: {

    }
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html"
    controller: ($scope, downloadService, translationService, $sce, $modal, $http) ->

        #change option of the modal
        $('#modalConfirmationExitForm').modal({
            backdrop: 'static'
        })

        modalName = 'CONFIRMATION_EXIT_FORM'
        show = true

        $scope.$on 'SHOW_MODAL_'+modalName,(event,args) ->
            console.log "je dois mouvrir"
            show = args.show


        ###

        #change option of the modal
        $('#modalLogin').modal({
            backdrop: 'static'
        })
        $('#modalLogin').modal('hide')

        #initialize the modal when it's displayed
        $('#modalLogin').on 'shown.bs.modal', (e) ->
            $scope.initialize()
            #refresh angular
            $scope.$apply()

        $scope.continue = ->
        ###

    link: (scope) ->

