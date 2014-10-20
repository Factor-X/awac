angular
.module('app.directives')
.directive "mmAwacModalVerificationAssign", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-assign.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.isLoading = true
        $scope.usersSelected = []


        downloadService.getJson 'awac/organization/getMyOrganization', (result) ->
            if not result.success
                messageFlash.displayError(result.data.message)
                $scope.isLoading = false
            else
                $scope.users = result.data.users
                if $scope.getParams().request.verifierList?
                    for user in $scope.getParams().request.verifierList
                        for userToTest in $scope.users
                            if user.identifier == userToTest.identifier
                                userToTest.selected = true
                        $scope.usersSelected.push user

                $scope.isLoading = false


        $scope.$watch 'users', ->
            $scope.usersSelected = []
            if $scope.users?
                for user in $scope.users
                    if user.selected == true
                        $scope.usersSelected.push user
        , true

        $scope.allFieldValid = ->
            return $scope.usersSelected.length > 0

        $scope.save = ()->
            if $scope.allFieldValid() == true
                $scope.isLoading = true

                usersSelectedIdentifier = []
                for user in $scope.usersSelected
                    usersSelectedIdentifier.push user.identifier

                dto =
                    newStatus: 'VERIFICATION_STATUS_VERIFICATION'
                    scopeId: $scope.getParams().request.scope.id
                    periodKey: $scope.getParams().request.period.key
                    verifierIdentifier: usersSelectedIdentifier

                downloadService.postJson "/awac/verification/setStatus", dto, (result) ->
                    if not result.success
                        $scope.isLoading = false
                    else
                        $scope.isLoading = false
                        $scope.getParams().request.status = 'VERIFICATION_STATUS_VERIFICATION'
                        $scope.getParams().request.verifierList = $scope.usersSelected
                        $scope.close()

        $scope.close = ->
            modalService.close modalService.VERIFICATION_ASSIGN


