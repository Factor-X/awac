angular
.module('app.directives')
.directive "mmAwacModalEditEvent", (directiveService, downloadService, translationService, messageFlash) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-edit-event.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.createNewEvent = true

        if $scope.getParams().site?
            $scope.site =  angular.copy($scope.getParams().site)
            $scope.createNewEvent =false
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

        $scope.close = ->
            modalService.close modalService.EDIT_EVENT

    link: (scope) ->

