angular
.module('app.directives')
.directive "mmAwacRegistrationHousehold", (directiveService,downloadService,messageFlash,translationService,modalService) ->
    restrict: "E"
    scope:{}
    templateUrl: "$/angular/templates/mm-awac-registration-household.html"
    replace:true
    controller: ($scope) ->


        $scope.validatePasswordConfirmField = () ->
            if ($scope.fields.passwordInfo.isValid? == true > 0 && $scope.fields.passwordInfo.field == $scope.fields.passwordConfirmInfo.field)
                return true
            return false

        $scope.fields =
            identifierInfo:
                fieldTitle: "USER_IDENTIFIER"
                inputName:'identifier'
                validationRegex: "^\\S{5,20}$"
                validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"

            lastNameInfo:
                fieldTitle: "USER_LASTNAME"
                inputName:'lastName'
                validationRegex: "^.{1,255}$"
                validationMessage: "USER_LASTNAME_WRONG_LENGTH"

            firstNameInfo:
                fieldTitle: "USER_FIRSTNAME"
                inputName:'firstName'
                fieldType: "text"
                validationRegex: "^.{1,255}$"
                validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
                focus: ->
                    return $scope.$parent.tabActive[2]

            emailInfo:
                fieldTitle: "USER_EMAIL"
                inputName:'email'
                validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
                validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"

            passwordInfo:
                fieldTitle: "USER_PASSWORD"
                inputName:'password'
                fieldType: "password"
                validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
                validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

            passwordConfirmInfo:
                fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
                inputName:'password'
                fieldType: "password"
                validationFct: $scope.validatePasswordConfirmField
                validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        $scope.aggrement_validation = false

        $scope.organizationStatisticsAllowed = true




        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false

            if $scope.aggrement_validation == false
                return false

            return true

        $scope.displayAgreement = () ->
            modalService.show(modalService.HELP, {template: 'agreement'})

        #send the request to the server
        $scope.registration = () ->

            if $scope.allFieldValid()

                #active loading mode
                $scope.isLoading = true

                data = {}
                data.person = {}
                data.person.email = $scope.fields.emailInfo.field
                data.person.identifier = $scope.fields.identifierInfo.field
                data.person.firstName = $scope.fields.firstNameInfo.field
                data.person.lastName = $scope.fields.lastNameInfo.field
                data.password = $scope.fields.passwordInfo.field
                data.organizationStatisticsAllowed = $scope.fields.organizationStatisticsAllowed
                data.person.defaultLanguage = $scope.$root.language


                #send request
                downloadService.postJson '/awac/registration/household', data, (result) ->
                    if result.success
                        $scope.$root.loginSuccess(result.data)
                        messageFlash.displaySuccess translationService.get "CONNECTION_MESSAGE_SUCCESS"
                        $scope.isLoading = false
                    else
                        #disactive loading mode
                        $scope.isLoading = false

                return false
