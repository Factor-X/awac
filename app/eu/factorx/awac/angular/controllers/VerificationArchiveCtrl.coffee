angular
.module('app.controllers')
.controller "VerificationArchiveCtrl", ($scope,displayLittleFormMenu,modalService,downloadService,messageFlash,$window,ngTableParams,$filter) ->

    $scope.displayLittleFormMenu=displayLittleFormMenu


    #launch download
    modalService.show(modalService.LOADING)
    downloadService.getJson "/awac/verification/archivedRequests", (result) ->
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

    $scope.downloadReport = (request)->
        url = '/awac/file/download/' + request.verificationSuccessFileId
        $window.open(url)
