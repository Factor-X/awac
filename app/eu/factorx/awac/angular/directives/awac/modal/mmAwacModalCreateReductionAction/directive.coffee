angular
.module('app.directives')
.directive "mmAwacModalCreateReductionAction", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='

    templateUrl: "$/angular/templates/mm-awac-modal-create-reduction-action.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope


        data =
            title: ''
            type: ''
            status: ''
            co2Gain: ''
            financialGain: ''
            investissement: ''
            investmentBack: ''
            deadline: ''
            assignee: ''
            note: ''
            documents: ''


        $scope.passwordInfo =
            field: ""
            fieldType: "password"
            inputName:'password'
            fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
            placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^\\S{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            hideIsValidIcon: true
            focus: ->
                return true

        $scope.oldEmailInfo =
            inputName:'email'
            field: $scope.getParams().oldEmail
            fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE"
            disabled: true

        $scope.newEmailInfo =
            inputName:'email'
            field: ""
            fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE"
            placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
            hideIsValidIcon: true

        #send the request to the server
        $scope.save = () ->

            $scope.isLoading = true

            data =
                password: $scope.passwordInfo.field
                newEmail: $scope.newEmailInfo.field

            downloadService.postJson '/awac/user/email/save', data, (result) ->
                if result.success
                    messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
                    $scope.close()
                    if $scope.getParams().cb?
                        $scope.getParams().cb($scope.newEmailInfo.field)
                else
                    messageFlash.displayError result.data.message
                    $scope.isLoading = false

            return false

        $scope.close = ->
            modalService.close modalService.EMAIL_CHANGE

    link: (scope) ->

