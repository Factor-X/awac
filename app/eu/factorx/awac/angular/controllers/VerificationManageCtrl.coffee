angular
.module('app.controllers')
.controller "VerificationManageCtrl", ($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, $filter, ngTableParams) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.loadingRequest = []

    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/verification/requestsToManage", (result) ->
        if not result.success
            messageFlash.displayError(result.data.message)
            modalService.close(modalService.LOADING)
        else
            $scope.requests = result.data.list
            $scope.displayTable()
            modalService.close(modalService.LOADING)

    $scope.displayTable = () ->
        if not $scope.requests
            $scope.requests=[]
        if $scope.tableParams?
            $scope.tableParams.reload();
        else
            $scope.tableParams = new ngTableParams(
                page: 1 # show first page
                count: 100 # count per page
                sorting:
                    code: "asc" # initial sorting
            ,
                total: 0 # length of data
                getData: ($defer, params) ->
                    orderedData = $filter("orderBy")($scope.requests, params.orderBy())
                    $defer.resolve orderedData.slice((params.page() - 1) * params.count(),
                            params.page() * params.count())
                    params.total $scope.requests.length
            )

    $scope.changeStatus = (request, newStatus) ->
        dto =
            scopeId: request.scope.id
            periodKey: request.period.key
            newStatus: newStatus

        $scope.loadingRequest[request.id] = true

        downloadService.postJson "/awac/verification/setStatus", dto, (result) ->
            if not result.success
                messageFlash.displayError(result.data.message)
                $scope.loadingRequest[request.id] = false
            else
                #change status
                request.status = newStatus
                $scope.loadingRequest[request.id] = false
                if newStatus == 'VERIFICATION_STATUS_REJECTED'
                    i=0
                    for r in $scope.requests
                        if r.id == request.id
                            $scope.requests.splice(i,1)
                        i++
                        
                $scope.displayTable()

    $scope.assignRequest = (request) ->
        params=
            request:request
        modalService.show(modalService.VERIFICATION_ASSIGN, params)

    $scope.addKey = () ->
        dto =
            key:angular.copy $scope.keyToInject
        $scope.addKeyLoading = true
        $scope.keyToInject=""
        downloadService.postJson "/awac/verification/addRequestByKey", dto, (result) ->
            if not result.success
                messageFlash.displayError(result.data.message)
                $scope.addKeyLoading = false
            else
                #change status
                $scope.requests.push result.data
                $scope.displayTable()
                $scope.addKeyLoading = false
