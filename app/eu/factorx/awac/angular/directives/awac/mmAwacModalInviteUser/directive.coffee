angular
.module('app.directives')
.directive "mmAwacModalInviteUser", (directiveService, downloadService, translationService, messageFlash, $rootScope) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-invite-user.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.inviteEmailInfo =
            field: ""
            fieldTitle: "USER_INVITATION_EMAIL_FIELD_TITLE"
            placeholder: "USER_INVITATION_EMAIL_FIELD_PLACEHOLDER"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
            focus: ->
                return true

        $scope.allFieldValid = () ->
            if $scope.inviteEmailInfo.isValid
                return true
            return false

        #send the request to the server
        $scope.save = () ->

            console.log("entering save() of Invite users");

            if !$scope.allFieldValid()
                return false

            $scope.isLoading = true

            data =
                invitationEmail: $scope.inviteEmailInfo.field
                organization: $rootScope.organization

            downloadService.postJson '/awac/invitation', data, (result) ->
                if result.success
                    messageFlash.displaySuccess "CHANGES_SAVED"
                    $scope.close()
#                    if $scope.getParams().cb?
#                        $scope.getParams().cb($scope.inviteEmailInfo.field)
                else
                    messageFlash.displayError result.data.message
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.INVITE_USER

    link: (scope) ->

