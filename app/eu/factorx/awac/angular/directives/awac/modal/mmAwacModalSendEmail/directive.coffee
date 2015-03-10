angular
.module('app.directives')
.directive "mmAwacModalSendEmail", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-send-email.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.fields = {
            title:
                fieldTitle: "TITLE"
                validationRegex: "^.{1,255}$"
                validationMessage: "FIELD_VALIDATION_TOO_LENGTH"
                focus: ->
                    return true
            content:
                fieldTitle: "CONTENT"
                validationRegex: /^.{1,65550}$/m
                validationMessage: "FIELD_VALIDATION_TOO_LENGTH"
                fieldType:'textarea'
        }

        $scope.allFieldValid = () ->
            for key in Object.keys($scope.fields)
                if key != '$$hashKey'
                    if !$scope.fields[key].isValid? || $scope.fields[key].isValid == false
                        return false
            return true

        $scope.save = ->
            if $scope.allFieldValid
                data =
                    calculatorKey: $scope.getParams().calculatorTarget
                    onlyOrganizationSharedData: $scope.getParams().onlyOrganizationSharedData
                    emailTitle: $scope.fields.title.field
                    emailContent: $scope.fields.content.field
                console.log data
                downloadService.postJson "/awac/admin/organizationData/sendEmail", data, (result) ->
                    if result.success
                        $scope.close()
                        messageFlash.displaySuccess translationService.get('EMAIL_SEND')


        $scope.close = ->
            modalService.close modalService.SEND_EMAIL

    link: (scope) ->
        scope.getAssociatedUsers()



