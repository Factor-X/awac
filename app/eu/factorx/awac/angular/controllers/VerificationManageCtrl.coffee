angular
.module('app.controllers')
.controller "VerificationManageCtrl", ($scope,displayLittleFormMenu,downloadService,modalService,messageFlash) ->

    $scope.displayLittleFormMenu=displayLittleFormMenu

    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/verification/requests/", (result) ->

        if not result.success
            # TODO ERROR HANDLING
            $scope.errorMessage = result.data.message
            messageFlash.displayError(result.data.message)
            modalService.close(modalService.LOADING)
        else
            $scope.requests = result.data

            modalService.close(modalService.LOADING)

            $scope.displayTable = () ->
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
                            $defer.resolve orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count())
                            params.total $scope.requests.length
                    )