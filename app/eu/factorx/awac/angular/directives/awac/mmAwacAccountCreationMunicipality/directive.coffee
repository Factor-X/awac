angular
.module('app.directives')
.directive "mmAwacAccountCreationMunicipality", (directiveService,downloadService,messageFlash) ->
    restrict: "E"
    scope:{}
    templateUrl: "$/angular/templates/mm-awac-account-creation-municipality.html"
    replace:true
    controller: ($scope) ->



        $scope.validatePasswordConfirmField = () ->
            if ($scope.passwordInfo.field == $scope.passwordConfirmInfo.field)
                return true
            $scope.passwordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION"
            return false

        $scope.identifierInfo =
            fieldTitle: "USER_IDENTIFIER"
            validationRegex: "[a-zA-Z0-9-]{5,20}"
            validationMessage: "IDENTIFIER_CHECK_WRONG"

        $scope.lastNameInfo =
            fieldTitle: "USER_LASTNAME"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_LASTNAME_WRONG_LENGTH"

        $scope.firstNameInfo =
            fieldTitle: "USER_FIRSTNAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
            focus: ->
                return $scope.$parent.tabActive[2]

        $scope.emailInfo =
            fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE"
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"

        $scope.passwordInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE"
            fieldType: "password"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        $scope.passwordConfirmInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            fieldType: "password"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        $scope.municipalityNameInfo =
            fieldTitle: "MUNICIPALITY_NAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "MUNICIPALITY_NAME_WRONG_LENGTH"



        $scope.registrationFieldValid = () ->
            if $scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.municipalityNameInfo.isValid
                return true
            return false

        #send the request to the server
        $scope.registration = () ->

            #active loading mode
            $scope.isLoading = true

            data = {}
            data.person = {}
            data.person.email = $scope.emailInfo.field
            data.person.identifier = $scope.identifierInfo.field
            data.person.firstName = $scope.firstNameInfo.field
            data.person.lastName = $scope.lastNameInfo.field
            data.password = $scope.passwordInfo.field
            data.municipalityName = $scope.municipalityNameInfo.field


            #send request
            downloadService.postJson '/municipality/registration', data, (result) ->
                if result.success
                    $scope.$root.loginSuccess(result.data)
                    messageFlash.displaySuccess "You are now connected"
                    $scope.isLoading = false
                else
                    #display the error message
                    messageFlash.displayError result.data.message
                    #disactive loading mode
                    $scope.isLoading = false

            return false
