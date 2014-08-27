angular
.module('app.controllers')
.controller "UserManagerCtrl", ($scope, translationService, modalService, downloadService,messageFlash) ->
    $scope.title = translationService.get('USER_MANAGER_TITLE')

    $scope.isLoading = {}
    $scope.isLoading['admin'] = {}
    $scope.isLoading['isActive'] = {}

    $scope.getUserList = ->
        return $scope.$root.users

    $scope.inviteUser = ->
        modalService.show(modalService.INVITE_USER)

    $scope.getMyself = ->
        return $scope.$root.currentPerson

    $scope.activeUser = (user) ->

        if $scope.getMyself().isAdmin == true && $scope.getMyself().email != user.email

            data = {}
            data.email = user.email
            data.interfaceName = $scope.$root.instanceName
            data.isActive = !user.isActive

            $scope.isLoading['isActive'][user.email] = true

            downloadService.postJson "/awac/user/activeAccount", data, (result) ->
                if result.success
                    user.isActive = !user.isActive
                    $scope.isLoading['isActive'][user.email] = false
                else
                    $scope.isLoading['isActive'][user.email] = false
                    messageFlash.displayError result.data.message


    $scope.isAdminUser = (user) ->

        if $scope.getMyself().isAdmin == true && $scope.getMyself().email != user.email && user.isActive == true

            data = {}
            data.email = user.email
            data.interfaceName = $scope.$root.instanceName
            data.isAdmin = !user.isAdmin

            $scope.isLoading['admin'][user.email] = true

            downloadService.postJson "/awac/user/isAdminAccount", data, (result) ->
                if result.success
                    $scope.isLoading['admin'][user.email] = false
                else
                    $scope.isLoading['admin'][user.email] = false
                    messageFlash.displayError result.data.message

    $scope.toForm = ->
        $scope.$parent.navToLastFormUsed()
