angular
.module('app.directives')
.directive "mmAwacModalVerificationDocument", (directiveService, downloadService, translationService, messageFlash,$window) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-document.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.fields = {

            myPassword:
                fieldType: "password"
                inputName: 'password'
                fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE"
                placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER"
                validationRegex: "^\\S{5,20}$"
                validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
                hideIsValidIcon: true
                focus: ->
                    return true

        }

        $scope.download =->
            url = '/awac/file/download/' + $scope.$root.verificationRequest.verificationSuccessFileId
            $window.open(url)

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        #send the request to the server
        $scope.save = (valid) ->
            if $scope.allFieldValid()
                $scope.isLoading = true

                if valid  == true
                    newStatus ='VERIFICATION_STATUS_VERIFIED'
                else
                    newStatus ='VERIFICATION_STATUS_VERIFICATION'

                data=
                    scopeId:$scope.$root.scopeSelectedId
                    periodKey:$scope.$root.periodSelectedKey
                    password:$scope.fields.myPassword.field
                    newStatus:newStatus

                downloadService.postJson "/awac/verification/setStatus", data, (result) ->
                    $scope.isLoading = false
                    if result.success

                        #display success message
                        $scope.$root.verificationRequest?.status =newStatus
                        #close window
                        $scope.close()


            return false

        $scope.close = ->
            modalService.close modalService.VERIFICATION_DOCUMENT

    link: (scope) ->

