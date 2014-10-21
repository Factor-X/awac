angular
.module('app.controllers')
.controller "UserManagerCtrl", ($scope, translationService, modalService, downloadService,messageFlash) ->
    $scope.title = translationService.get('USER_MANAGER_TITLE')

    $scope.isLoading = {}
    $scope.isLoading['admin'] = {}
    $scope.isLoading['isActive'] = {}

    # load my organization
    modalService.show(modalService.LOADING)
    downloadService.getJson 'awac/organization/getMyOrganization', (result) ->

        if not result.success
            messageFlash.displayError translationService.get 'UNABLE_LOAD_DATA'
            modalService.close(modalService.LOADING)
        else
            modalService.close(modalService.LOADING)
            $scope.organization = result.data

            $scope.getUserList = ->
                return $scope.organization.users

            $scope.inviteUser = ->
                modalService.show(modalService.INVITE_USER)

            $scope.getMyself = ->
                return $scope.$root.currentPerson

            $scope.activeUser = (user) ->

                if $scope.getMyself().isAdmin == true && $scope.getMyself().email != user.email

                    data = {}
                    data.identifier = user.identifier
                    data.isActive = !user.isActive

                    $scope.isLoading['isActive'][user.email] = true

                    downloadService.postJson "/awac/user/activeAccount", data, (result) ->
                        if result.success
                            user.isActive = !user.isActive
                            $scope.isLoading['isActive'][user.email] = false
                        else
                            $scope.isLoading['isActive'][user.email] = false


            $scope.isAdminUser = (user) ->

                if $scope.getMyself().isAdmin == true && $scope.getMyself().email != user.email && user.isActive == true

                    data = {}
                    data.identifier = user.identifier
                    data.isAdmin = !user.isAdmin

                    $scope.isLoading['admin'][user.email] = true

                    downloadService.postJson "/awac/user/isAdminAccount", data, (result) ->
                        if result.success
                            $scope.isLoading['admin'][user.email] = false
                        else
                            $scope.isLoading['admin'][user.email] = false

            $scope.isMainVerifier = (user) ->

                if $scope.getMyself().isAdmin == true && $scope.getMyself().email != user.email && user.isActive == true

                    data = {}
                    data.identifier = user.identifier
                    data.isMainVerifier = !user.isMainVerifier

                    $scope.isLoading['isMainVerifier'][user.email] = true

                    downloadService.postJson "/awac/user/isMainVerifier", data, (result) ->
                        $scope.isLoading['isMainVerifier'][user.email] = false

    $scope.toForm = ->
        $scope.$root.navToLastFormUsed()
