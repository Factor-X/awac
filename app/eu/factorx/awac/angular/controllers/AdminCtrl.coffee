angular
.module('app.controllers')
.controller "AdminCtrl", ($scope, downloadService) ->

    $scope.notifications = []

    downloadService.getJson "admin/get_notifications", (dto) ->

        if dto?
            $scope.notifications = dto.notifications;

        return
