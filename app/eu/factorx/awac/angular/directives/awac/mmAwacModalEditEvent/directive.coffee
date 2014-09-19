angular
.module('app.directives')
.directive "mmAwacModalEditEvent", (directiveService, downloadService, translationService, messageFlash ) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-edit-event.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.createNewEvent = true

        $scope.assignedPeriodStruct = { key:"2013",label:"2013" }

        if $scope.getParams().event?
            $scope.event =  angular.copy($scope.getParams().event)
            $scope.createNewEvent = false
            $scope.assignedPeriod = $scope.event.period.label
        else
            $scope.event = {}
            if $scope.$root.periods?
              $scope.assignedPeriod = $scope.$root.periods[0].label
            else
              $scope.assignedPeriod = $scope.assignedPeriodStruct.label

        $scope.fields = {

            name :{
                fieldTitle: "NAME"
                field:$scope.event.name
                validationRegex: "^.{1,255}$"
                validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
                focus: ->
                    return true
            }

            description :{
                fieldTitle: "DESCRIPTION"
                validationRegex: "^.{0,65000}$"
                validationMessage: "CONTROL_FIELD_DEFAULT_TEXT"
                field:$scope.event.description
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

                console.log "AssignedPeriod"
                console.log $scope.assignedPeriod

                #create DTO
                data = {}
                data.organization = $scope.event.organization
                #data.period = $scope.event.period
                data.period = $scope.$root.periods[0]
                data.period.key = $scope.assignedPeriod
                data.period.label = $scope.assignedPeriod
                data.id = $scope.event.id
                data.name = $scope.fields.name.field
                data.description = $scope.fields.description.field
                #data.period.key=$scope.assignedPeriod
                #data.period.label=$scope.assignedPeriod

                $scope.isLoading = true

                console.log data

                if $scope.getParams().event?

                    #edit event
                    data.id = $scope.getParams().event.id

                    downloadService.postJson '/awac/organization/events/save', data, (result) ->
                        if result.success

                            #display success message
                            messageFlash.displaySuccess "CHANGES_SAVED"

                            #edit site
                            $scope.getParams().event.name = $scope.fields.name.field
                            $scope.getParams().event.description = $scope.fields.description.field
                            $scope.getParams().event.period.label = $scope.assignedPeriod
                            $scope.getParams().event.period.key = $scope.assignedPeriod

                            #close window
                            $scope.close()
                        else
                            #display success message
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
                else
                    #create event
                    data.organization = $scope.getParams().organization
                    data.period = $scope.$root.periods[0]
                    data.id = 0
                    downloadService.postJson '/awac/organization/events/save', data, (result) ->
                        if result.success

                            #display success message
                            messageFlash.displaySuccess "CHANGES_SAVED"

                            # add new event to the list
                            $scope.getParams().events[$scope.getParams().events.length] = result.data

                            #close window
                            $scope.close()
                        else
                            messageFlash.displayError result.data.message
                            $scope.isLoading = false
            return false

        $scope.close = ->
            modalService.close modalService.EDIT_EVENT

    link: (scope) ->

