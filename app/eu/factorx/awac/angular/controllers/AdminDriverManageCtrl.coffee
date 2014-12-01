angular
.module('app.controllers')
.controller "AdminDriverManageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, $location) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu


    $scope.tempIdCounter = 0

    $scope.drivers = null
    wasEdited=false

    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/admin/driver/all", (result) ->
        modalService.close(modalService.LOADING)
        if result.success == true
            $scope.drivers = result.data.list
            # assign tempId for remove
            for driver in $scope.drivers
                for value in driver.driverValues
                    value.tempId = ++$scope.tempIdCounter

            $scope.orderDriverValues()
            $scope.originalDrivers = angular.copy($scope.drivers)


    $scope.wasEdited = () ->
        wasEdited=true

    $scope.addValue = (driver) ->
        driver.driverValues.push {tempId: ++$scope.tempIdCounter}
        wasEdited=true

    $scope.getPeriod = (driver, currentDriverValue) ->
        periods = []
        for period in $scope.$root.periods
            founded = false
            for driverValue in driver.driverValues
                if period.key == driverValue?.fromPeriodKey && period.key != currentDriverValue?.fromPeriodKey
                    founded = true
                    break
            if founded == false
                periods.push period
        return periods

    $scope.orderDriverValues = ->
        for driver in $scope.drivers
            driverValues = []
            for driverValue in driver.driverValues
                i = 0
                founded = false
                for sortedDriver in driverValues
                    if parseFloat(sortedDriver.fromPeriodKey) > parseFloat(driverValue.fromPeriodKey)
                        driverValues.splice(i, 0, driverValue)
                        founded = true
                        break
                    i++
                if founded == false
                    driverValues.push driverValue
            driver.driverValues = driverValues

    $scope.save = ->
        modalService.show modalService.LOADING
        downloadService.postJson '/awac/admin/driver/update', {list: $scope.drivers}, (result) ->
            modalService.close modalService.LOADING
            wasEdited=false

    $scope.remove = (driver, valueTempId) ->
        params =
            titleKey: 'DRIVER_CONFIRM_REMOVE_MODAL_TITLE'
            messageKey: 'DRIVER_CONFIRM_REMOVE_MODAL_BODY'
            onConfirm: $scope.removeConfirmed
            confirmParams: [
                driver
                valueTempId
            ]

        modalService.show modalService.CONFIRM_DIALOG, params

    $scope.removeConfirmed = (driver, valueTempId) ->
        i = 0
        for value in driver.driverValues
            if value.tempId == valueTempId
                driver.driverValues.splice(i, 1)
                console.log "wasEdited => remove"
                wasEdited=true
                break
            i++

    #
    # Check if driver is modified
    #
    $scope.isModified = (drivers) ->
        original = _.find $scope.originalDrivers, (o) ->
            return o.key == drivers.key

        compared = _.omit(drivers, 'typeString')

        eq = angular.equals(original, compared)
        console.log original
        console.log compared
        console.log eq

        return !eq

    $scope.ignoreChanges = false

    $scope.$root.$on '$locationChangeStart', (event, next, current) ->
        return unless !$scope.ignoreChanges
        if wasEdited == true
            #return unless _.any($scope.drivers, $scope.isModified) and !$scope.ignoreChanges

            # stop changing
            event.preventDefault()

            # show confirm
            params =
                titleKey: "DIVERS_CANCEL_CONFIRMATION_TITLE"
                messageKey: "DIVERS_CANCEL_CONFIRMATION_MESSAGE"
                onConfirm: () ->
                    $scope.ignoreChanges = true
                    $location.path(next.split('#')[1])
            modalService.show modalService.CONFIRM_DIALOG, params



