angular
.module('app.directives')
.directive "mmAwacModalEditSite", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-edit-site.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.site =  angular.copy($scope.getParams().site)

        $scope.fields = {

            name :{
                fieldTitle: "NAME"
                field:$scope.site.name
                validationRegex: "^.{1,255}$"
                validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
                focus: ->
                    return true
            }

            description :{
                fieldTitle: "DESCRIPTION"
                validationRegex: "^.{0,65000}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_TEXT"
                field:$scope.site.description
                hideIsValidIcon: true
            }
            nace :{
                fieldTitle: "SITE_MANAGER_NACE_CODE"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.naceCode
                hideIsValidIcon: true
            }
            orgStructure :{
                fieldTitle: "SITE_MANAGER_ORGANIZATIONAL_STRUCTURE"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.organizationalStructure
                hideIsValidIcon: true
            }
            ecoInterest :{
                fieldTitle: "SITE_MANAGER_ECONOMIC_INTEREST"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.economicInterest
                hideIsValidIcon: true
            }
            opePolicy :{
                fieldTitle: "SITE_MANAGER_OPERATING_POLICY"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.operatingPolicy
                hideIsValidIcon: true
            }
            accountingTreatment :{
                fieldTitle: "SITE_MANAGER_ACCOUNTING_TREATMENT"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.accountingTreatment
                hideIsValidIcon: true
            }
            percentOwned :{
                fieldTitle: "SITE_MANAGER_PERCENT_OWNED"
                validationFct:->
                    if $scope.fields.percentOwned.field>=0 && $scope.fields.percentOwned.field<=100
                        return true
                    return false
                validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100"
                field:$scope.site.percentOwned
                hideIsValidIcon: true
                fieldType : 'double'
            }
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

                #create DTO
                data = {}
                data.name = $scope.fields.name.field
                data.description = $scope.fields.name.field
                data.naceCode = $scope.fields.nace.field
                data.organizationalStructure = $scope.fields.orgStructure.field
                data.economicInterest = $scope.fields.ecoInterest.field
                data.operatingPolicy = $scope.fields.opePolicy.field
                data.accountingTreatment = $scope.fields.accountingTreatment.field
                data.percentOwned = $scope.fields.percentOwned.field

                $scope.isLoading = true

                if $scope.getParams().site?

                    #edit site
                    data.id = $scope.getParams().site.id
                    downloadService.postJson '/awac/site/edit', data, (result) ->
                        if result.success
                            messageFlash.displaySuccess "CHANGES_SAVED"
                            $scope.close()
                        else
                            # TODO ERROR HANDLING
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
                else
                    #create site
                    downloadService.postJson '/awac/site/create', data, (result) ->
                        if result.success
                            messageFlash.displaySuccess "CHANGES_SAVED"
                            $scope.close()
                        else
                            # TODO ERROR HANDLING
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
            return false

        $scope.close = ->
            modalService.close modalService.EDIT_SITE

    link: (scope) ->

