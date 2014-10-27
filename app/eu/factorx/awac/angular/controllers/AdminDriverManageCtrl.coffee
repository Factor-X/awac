angular
.module('app.controllers')
.controller "AdminDriverManageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu


    $scope.tempIdCounter = 0

    $scope.drivers = null

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


    $scope.addValue = (driver) ->
        driver.driverValues.push {tempId: ++$scope.tempIdCounter}
        console.log $scope.tempIdCounter

    $scope.getPeriod = (driver, currentDriverValue) ->
        periods = []
        for period in $scope.$root.periods
            founded=false
            for driverValue in driver.driverValues
                if period.key == driverValue?.fromPeriodKey && period.key != currentDriverValue?.fromPeriodKey
                    founded=true
                    break
            if founded==false
                periods.push period
        return periods

    $scope.orderDriverValues = ->
        for driver in $scope.drivers
            driverValues = []
            for driverValue in driver.driverValues
                i=0
                founded=false
                for sortedDriver in driverValues
                    if parseFloat(sortedDriver.fromPeriodKey)  > parseFloat(driverValue.fromPeriodKey)
                        driverValues.splice(i,0,driverValue)
                        founded=true
                        break
                    i++
                if founded==false
                    driverValues.push driverValue
            driver.driverValues = driverValues

    $scope.save = ->
        modalService.show modalService.LOADING
        downloadService.postJson '/awac/admin/driver/update', {list:$scope.drivers}, (result) ->
            modalService.close modalService.LOADING

    $scope.remove =(driver,valueTempId) ->
        i=0
        for value in driver.driverValues
            if value.tempId == valueTempId
                driver.driverValues.splice(i,1)
                break
            i++



