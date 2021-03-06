angular
.module('app.directives')
.directive "mmAwacRegistrationEnterprise", (directiveService,downloadService,messageFlash,translationService,modalService) ->
    restrict: "E"
    scope:{}
    templateUrl: "$/angular/templates/mm-awac-registration-enterprise.html"
    replace:true
    controller: ($scope) ->

        $scope.validatePasswordConfirmField = () ->
            if ($scope.passwordInfo.isValid? == true > 0 && $scope.passwordInfo.field == $scope.passwordConfirmInfo.field)
                return true
            return false

        $scope.identifierInfo =
            fieldTitle: "USER_IDENTIFIER"
            inputName:'identifier'
            validationRegex: "^\\S{5,20}$"
            validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"

        $scope.lastNameInfo =
            fieldTitle: "USER_LASTNAME"
            inputName:'lastName'
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_LASTNAME_WRONG_LENGTH"

        $scope.firstNameInfo =
            fieldTitle: "USER_FIRSTNAME"
            inputName:'firstName'
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "USER_FIRSTNAME_WRONG_LENGTH"
            focus: ->
                return $scope.$parent.tabActive[2]

        $scope.emailInfo =
            fieldTitle: "USER_EMAIL"
            inputName:'email'
            validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"

        $scope.passwordInfo =
            fieldTitle: "USER_PASSWORD"
            inputName:'password'
            fieldType: "password"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        $scope.passwordConfirmInfo =
            fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE"
            inputName:'password'
            fieldType: "password"
            validationFct: $scope.validatePasswordConfirmField
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"

        $scope.organizationNameInfo =
            fieldTitle: "ORGANIZATION_NAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"

        $scope.organizationStatisticsAllowed = true

        $scope.firstSiteNameInfo =
            fieldTitle: "MAIN_SITE_NAME"
            fieldType: "text"
            validationRegex: "^.{1,255}$"
            validationMessage: "SITE_NAME_WRONG_LENGTH"

        $scope.aggrement_validation = false



        $scope.registrationFieldValid = () ->
            if $scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.organizationNameInfo.isValid && $scope.firstSiteNameInfo.isValid && $scope.aggrement_validation==true
                return true
            return false

        $scope.displayAgreement = () ->
            modalService.show(modalService.HELP, {template: 'agreement'})

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
            data.organizationName = $scope.organizationNameInfo.field
            data.organizationStatisticsAllowed = $scope.organizationStatisticsAllowed
            data.firstSiteName = $scope.firstSiteNameInfo.field
            data.person.defaultLanguage = $scope.$root.language


            #send request
            downloadService.postJson '/awac/registration/enterprise', data, (result) ->
                if result.success
                    $scope.$root.loginSuccess(result.data)
                    messageFlash.displaySuccess translationService.get "CONNECTION_MESSAGE_SUCCESS"
                    $scope.isLoading = false
                else
                    #disactive loading mode
                    $scope.isLoading = false

            return false
