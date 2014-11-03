angular
.module('app.controllers')
.controller "UserManagerCtrl", ($scope, translationService, modalService, downloadService, messageFlash) ->
    $scope.title = translationService.get('USER_MANAGER_TITLE')


    $scope.statusesForVerification = [
        id: 1
        label: 'SIMPLE_USER'
    ,
        id: 2
        label: 'USER_MANAGER_MAIN_VERIFIER'
    ,
        id: 3
        label: 'USER_MANAGER_ADMINISTRATOR'
    ]

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

            #compute tempStatus for verification
            if $scope.$root.instanceName == 'verification'
                for user in $scope.organization.users
                    if user.isAdmin == true
                        user.tempStatus = 3
                    else if user.isMainVerifier == true
                        user.tempStatus = 2
                    else
                        user.tempStatus = 1

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

            $scope.changeStatusForVerification = (user) ->
                data = {}
                data.identifier = user.identifier

                if user.tempStatus == 1
                    data.newStatus = 'user'
                else if user.tempStatus == 2
                    data.newStatus = 'main_verifier'
                else if user.tempStatus == 3
                    data.newStatus = 'admin'

                $scope.isLoading['admin'][user.email] = true

                console.log '!!!!'
                console.log data

                downloadService.postJson "/awac/user/setStatus", data, (result) ->
                    $scope.isLoading['admin'][user.email] = false

    $scope.toForm = ->
        if $scope.$root.mySites.length > 0
            $scope.$root.scopeSelectedId = $scope.$root.mySites[0].id
            $scope.$root.periodSelectedKey = $scope.$root.mySites[0].listPeriodAvailable[0].key
            $scope.$root.navToLastFormUsed()
        else
            $scope.$root.scopeSelectedId = undefined
            $scope.$root.periodSelectedKey = undefined
            $scope.$root.nav('/noScope')
