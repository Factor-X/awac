angular
.module('app.controllers')
.controller "UserManagerCtrl", ($scope, translationService, modalService, downloadService) ->
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
        data = {}
        data.email = user.email
        data.interfaceName = $scope.$root.instanceName
        data.isActive = !user.isActive

        $scope.isLoading['isActive'][user] = true

        downloadService.postJson "/awac/user/activeAccount", data, (result) ->
            if result.success
                user.isActive = !user.isActive
                $scope.isLoading['isActive'][user] = false
            else
                $scope.isLoading['isActive'][user] = false
                #todo display error message


    $scope.isAdminUser = (user) ->
        data = {}
        data.email = user.email
        data.interfaceName = $scope.$root.instanceName
        data.isAdmin = user.isAdmin

        $scope.isLoading['admin'][user] = true

        downloadService.postJson "/awac/user/isAdminAccount", data, (result) ->
            if result.success
                $scope.isLoading['admin'][user] = false
            else
                $scope.isLoading['admin'][user] = false
                #todo display error message
