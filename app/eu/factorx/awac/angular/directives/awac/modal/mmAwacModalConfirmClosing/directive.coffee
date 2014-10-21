angular
.module('app.directives')
.directive "mmAwacModalConfirmClosing", (directiveService, downloadService, translationService, messageFlash, $filter) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-closing.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.isLoading = false

        $scope.close = ->
            modalService.close modalService.CONFIRM_CLOSING


        $scope.passwordInfo =
            fieldTitle: "USER_PASSWORD"
            fieldType: "password"
            placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER"
            validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$"
            validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
            field: ""
            hideIsValidIcon: true
            focus: ->
                return true

        $scope.allFieldValid = () ->
            if $scope.passwordInfo.isValid
                return true
            return false


        $scope.valid = ->

            if $scope.allFieldValid()
                close = !$scope.$root.closedForms

                $scope.isLoading = true

                dto = {}
                dto.password = $scope.passwordInfo.field
                dto.periodKey = $scope.$root.periodSelectedKey
                dto.scopeId = $scope.$root.scopeSelectedId
                dto.close = close

                downloadService.postJson "/awac/answer/closeForm/",dto, (result)->
                    if result.success
                        $scope.$root.closedForms = close
                        if close
                            messageFlash.displaySuccess $filter('translate')('MODAL_CONFIRM_CLOSING_SUCCESS')
                        else
                            messageFlash.displaySuccess $filter('translate')('MODAL_CONFIRM_UNCLOSING_SUCCESS')
                        $scope.isLoading = false
                        $scope.close()
                    else
                        $scope.isLoading = false




