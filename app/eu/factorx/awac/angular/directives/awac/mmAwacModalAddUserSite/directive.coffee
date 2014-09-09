angular
.module('app.directives')
.directive "mmAwacModalAddUserSite", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-site.html"

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
                    if $scope.fields.percentOwned.field>=0 && $scope.fields.percentOwned.field<=1000
                        return true
                    return false
                validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100"
                field:$scope.site.percentOwned
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
                            messageFlash.displaySuccess "CHANGES_SAVED"

                            #edit site
                            $scope.getParams().site.name = $scope.fields.name.field
                            $scope.getParams().site.description = $scope.fields.description.field
                            $scope.getParams().site.naceCode = $scope.fields.nace.field
                            $scope.getParams().site.organizationalStructure = $scope.fields.orgStructure.field
                            $scope.getParams().site.economicInterest = $scope.fields.ecoInterest.field
                            $scope.getParams().site.operatingPolicy = $scope.fields.opePolicy.field
                            $scope.getParams().site.accountingTreatment = $scope.fields.accountingTreatment.field
                            $scope.getParams().site.percentOwned = $scope.fields.percentOwned.field

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
                            messageFlash.displaySuccess "CHANGES_SAVED"

                            # add new site to the list
                            $scope.$root.organization.sites[$scope.$root.organization.sites.length] = result.data

                            #close window
                            $scope.close()
                        else
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
            return false

        # user selection - begin
        $scope.employees=[{name:'John', age:25, gender:'boy'},
            {name:'Jessie', age:30, gender:'girl'},
            {name:'Johanna', age:28, gender:'girl'},
            {name:'Joy', age:15, gender:'girl'},
            {name:'Mary', age:28, gender:'girl'},
            {name:'Peter', age:95, gender:'boy'},
            {name:'Sebastian', age:50, gender:'boy'},
            {name:'Erika', age:27, gender:'girl'},
            {name:'Patrick', age:40, gender:'boy'},
            {name:'Samantha', age:60, gender:'girl'}];

        $scope.selection=[];

        # toggle selection for a given employee by name
        $scope.toggleSelection = (employeeName) ->
            console.log "entering toggleSelection"
            console.log employeeName

            idx = $scope.selection.indexOf(employeeName)
            console.log idx

            # is currently selected
            if idx > -1
              # remove from selection
              $scope.selection.splice(idx,1);
              # is newly selected
            else
              # add to selection
              $scope.selection.push(employeeName);
        # user selection - end

        $scope.close = ->
            modalService.close modalService.ADD_USER_SITE



    link: (scope) ->

