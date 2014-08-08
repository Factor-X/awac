angular
.module('app.directives')
.directive "mmAwacModalConfirmationExitForm", (directiveService) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html"
    controller: ($scope,modalService) ->
        directiveService.autoScope

        $scope.close= ->
            modalService.close modalService.CONFIRMATION_EXIT_FORM

        $scope.continue = ->

            arg={}
            arg.loc = $scope.ngParams.loc
            arg.confirmed = true
            $scope.$root.$broadcast('NAV', arg)
            $scope.close()

        $scope.save= ->

            arg={}
            arg.loc = $scope.ngParams.loc
            arg.confirmed = true
            $scope.$root.$broadcast 'SAVE_AND_NAV',arg
            $scope.close()



    link: (scope) ->

