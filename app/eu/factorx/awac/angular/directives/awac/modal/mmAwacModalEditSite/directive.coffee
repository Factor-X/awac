angular
.module('app.directives')
.directive "mmAwacModalEditSite", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-edit-site.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.createNewSite = true

        if $scope.getParams().site?
            $scope.site =  angular.copy($scope.getParams().site)
            $scope.createNewSite =false
        else
            $scope.site = {}
            #create a default value for percentOwned
            $scope.site.percentOwned = 100

        $scope.displayOptions = ->
            result = $scope.field.percentOwned.field == 100
            console.log result
            return result

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
                hidden: $scope.displayOptions
            }
            ecoInterest :{
                fieldTitle: "SITE_MANAGER_ECONOMIC_INTEREST"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.economicInterest
                hideIsValidIcon: true
                hidden: $scope.displayOptions
            }
            opePolicy :{
                fieldTitle: "SITE_MANAGER_OPERATING_POLICY"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.operatingPolicy
                hideIsValidIcon: true
                hidden: $scope.displayOptions
            }
            accountingTreatment :{
                fieldTitle: "SITE_MANAGER_ACCOUNTING_TREATMENT"
                validationRegex: "^.{0,255}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_STRING"
                field:$scope.site.accountingTreatment
                hideIsValidIcon: true
                hidden: $scope.displayOptions
            }
            percentOwned :{
                fieldTitle: "SITE_MANAGER_PERCENT_OWNED"
                validationFct:->
                    if $scope.fields.percentOwned.field>0 && $scope.fields.percentOwned.field<=100
                        return true
                    return false
                validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100"
                field:$scope.site.percentOwned
                numbersOnly: 'double'
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
                data.description = $scope.fields.description.field
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

                            #display success message
                            messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

                            #edit site
                            $scope.getParams().site.name = $scope.fields.name.field
                            $scope.getParams().site.description = $scope.fields.description.field
                            $scope.getParams().site.naceCode = $scope.fields.nace.field
                            $scope.getParams().site.organizationalStructure = $scope.fields.orgStructure.field
                            $scope.getParams().site.economicInterest = $scope.fields.ecoInterest.field
                            $scope.getParams().site.operatingPolicy = $scope.fields.opePolicy.field
                            $scope.getParams().site.accountingTreatment = $scope.fields.accountingTreatment.field
                            $scope.getParams().site.percentOwned = $scope.fields.percentOwned.field

                            #refresh my site
                            $scope.getParams().refreshMySites()

                            #close window
                            $scope.close()
                        else
                            #display success message
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
                else
                    #create site
                    downloadService.postJson '/awac/site/create', data, (result) ->
                        if result.success

                            #display success message
                            messageFlash.displaySuccess translationService.get "CHANGES_SAVED"

                            # add new site to the list
                            $scope.getParams().organization.sites.push result.data

                            # add to mySite
                            $scope.$root.mySites.push result.data

                            #refresh
                            $scope.getParams().refreshMySites()

                            #close window
                            $scope.close()
                        else
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
            return false

        $scope.close = ->
            modalService.close modalService.EDIT_SITE

    link: (scope) ->

