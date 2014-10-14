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
        return $scope.nameInfo.isValid && (($scope.nameInfo.field != $scope.organization.name) ||
            ($scope.statisticsAllowed != $scope.organization.statisticsAllowed))

    $scope.nameInfo =
        fieldTitle: "ORGANIZATION_NAME"
        validationRegex: "^.{1,255}$"
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
        field: null
        hideIsValidIcon: true
        isValid: true
        focus: ->
            return true

    $scope.statisticsAllowed = false

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
                if !$scope.allFieldValid
                    return false

                data =
                	name: $scope.nameInfo.field
                	statisticsAllowed: $scope.statisticsAllowed

                $scope.isLoading = true
                downloadService.postJson '/awac/organization/update', data, (result) ->
                    $scope.isLoading = false
                    if result.success
                        messageFlash.displaySuccess "CHANGES_SAVED"
                        $scope.$root.organizationName = $scope.nameInfo.field
                    else
                        messageFlash.displayError result.data.message
                    return false

    $scope.$watch 'selectedPeriodForEvent', () ->
        downloadService.getJson 'awac/organization/events/byPeriod/'+$scope.selectedPeriodForEvent,  (result) ->
            if result.success
                $scope.events = result.data.organizationEventList
            else
                messageFlash.displayError result.data.message