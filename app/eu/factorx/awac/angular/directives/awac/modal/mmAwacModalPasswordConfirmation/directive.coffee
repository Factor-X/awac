angular
.module('app.directives')
.directive "mmAwacModalPasswordConfirmation", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-password-confirmation.html"

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

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        #send the request to the server
        $scope.save = () ->
            if $scope.allFieldValid()
                $scope.isLoading = true

                data = $scope.getParams().data
                data.password=$scope.fields.myPassword.field

                downloadService.postJson $scope.getParams().url, data, (result) ->
                    $scope.isLoading = false
                    if result.success

                        #display success message
                        if $scope.getParams().successMessage?
                            messageFlash.displaySuccess translationService.get $scope.getParams().successMessage
                        if $scope.getParams().afterSave?
                            $scope.getParams().afterSave()
                        #close window
                        $scope.close()


            return false

        $scope.close = ->
            modalService.close modalService.PASSWORD_CONFIRMATION

    link: (scope) ->

