angular
.module('app.directives')
.directive "mmAwacModalEmailChange", (directiveService, downloadService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.password = ''
        $scope.oldEmail = $scope.ngParams.oldEmail
        $scope.newEmail = ''

        $scope.save = ->
            $scope.ngParams.saveNewEmail { password: $scope.password, oldEmail: $scope.oldEmail, newEmail: $scope.newEmail }, (result) ->
                if result
                    modalService.close modalService.EMAIL_CHANGE

        $scope.close = ->
            modalService.close modalService.EMAIL_CHANGE

    link: (scope) ->

