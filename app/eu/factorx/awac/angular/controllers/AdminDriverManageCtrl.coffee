angular
.module('app.controllers')
.controller "AdminDriverManageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    
    $scope.drivers = null

    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/admin/driver/all", (result) ->
        modalService.close(modalService.LOADING)
        console.log result.data.list
        if result.success == true
            $scope.drivers = result.data.list
            $scope.orderDriverValues()


    $scope.addValue = (driver) ->
        driver.driverValues.push {}

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
                console.log "try to add "
                console.log driverValue
                for sortedDriver in driverValues
                    console.log parseFloat(sortedDriver.fromPeriodKey)+"<->"+parseFloat(driverValue.fromPeriodKey)
                    if parseFloat(sortedDriver.fromPeriodKey)  > parseFloat(driverValue.fromPeriodKey)
                        console.log "ok"
                        driverValues.splice(i,0,driverValue)
                        founded=true
                        break
                    i++
                if founded==false
                    console.log "add at the end"
                    driverValues.push driverValue
                console.log driverValues
            driver.driverValues = driverValues

    $scope.save = ->
        modalService.show modalService.LOADING
        console.log $scope.drivers
        downloadService.postJson '/awac/admin/driver/update', {list:$scope.drivers}, (result) ->
            modalService.close modalService.LOADING

    $scope.remove =(driver,value) ->
        delete driver.driverValues[value]




