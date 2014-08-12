angular
.module('app.directives')
.directive "mmAwacModalConfirmationExitForm", (directiveService) ->
    restrict: "E"
    scope: {

    }
    templateUrl: "assets/html/sources/app/eu/factorx/awac/angular/directives/awac/mmAwacModalConfirmationExitForm/template.html"
    controller: ($scope,modalService) ->

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


        $scope.display = (params)->
            $scope.show = true
            if params.loc != undefined
                $scope.loc = params.loc


        $scope.close= ->
            $scope.show = false
            modalService.hide "SHOW_MODAL_"+modalName

        $scope.continue = ->
            arg={}
            arg.loc = $scope.loc
            arg.confirmed = true
            $scope.$root.$broadcast('NAV', arg)
            $scope.close()

        $scope.save= ->
            arg={}
            arg.loc = $scope.loc
            arg.confirmed = true
            $scope.$root.$broadcast 'SAVE_AND_NAV',arg
            $scope.close()



    link: (scope) ->

