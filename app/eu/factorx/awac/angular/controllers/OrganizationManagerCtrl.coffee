angular
.module('app.controllers')
.controller "OrganizationManagerCtrl", ($scope, translationService, modalService, downloadService, messageFlash) ->
    $scope.isLoading = false
    if $scope.$root.periods?
        $scope.assignPeriod = $scope.$root.periods[0].key
    else
        $scope.$watch '$root.periods', ->
            $scope.assignPeriod = $scope.$root.periods[0].key
    $scope.isPeriodChecked = {}

    $scope.selectedPeriodForEvent = $scope.$root.periods[0].key

    $scope.allFieldValid = () ->
        return $scope.nameInfo?.isValid && ($scope.nameInfo.field != $scope.organization.name) &&
            $scope.statisticsAllowed? && ($scope.statisticsAllowed != $scope.organization.statisticsAllowed)

    $scope.nameInfo =
        fieldTitle: "ORGANIZATION_NAME"
        validationRegex: "^.{1,255}$"
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
        field: null
        hideIsValidIcon: true
        isValid: true
        focus: ->
            return true

    # load my organization
    modalService.show(modalService.LOADING)
    downloadService.getJson 'awac/organization/getMyOrganization', (result) ->
        modalService.close(modalService.LOADING)
        if not result.success
            messageFlash.displayError translationService.get('UNABLE_LOAD_DATA')
        else
            $scope.organization = result.data

            $scope.nameInfo.field = $scope.organization.name
            $scope.statisticsAllowed = $scope.organization.statisticsAllowed

            $scope.events = []

            data = {}
            data.organization = $scope.organization
            data.period = $scope.$root.periods[0]

            downloadService.postJson 'awac/organization/events/load', data, (result) ->
                $scope.events = result.data.organizationEventList

            $scope.toForm = ->
                $scope.$root.navToLastFormUsed()

            $scope.getEventList = () ->
                return $scope.events

            $scope.editOrCreateEvent = (event) ->
                params = {}
                params.organization = $scope.organization
                params.events = $scope.events
                for period in $scope.$root.periods
                    if period.key == $scope.selectedPeriodForEvent
                        params.period = period
                if event?
                    params.event = event
                modalService.show(modalService.EDIT_EVENT, params)

            #send data
            $scope.saveOrganization = () ->
                console.log("save organization")
                if !$scope.allFieldValid
                    return false

                $scope.isLoading = true

                data =
                	name: $scope.nameInfo.field
                	statisticsAllowed: $scope.statisticsAllowed

                downloadService.postJson '/awac/organization/update', data, (result) ->
                    if result.success
                        messageFlash.displaySuccess "CHANGES_SAVED"
                        $scope.$root.organizationName = $scope.nameInfo.field
                        $scope.isLoading = false
                    else
                        messageFlash.displayError result.data.message
                        $scope.isLoading = false

                    return false
        