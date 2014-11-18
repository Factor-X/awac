angular
.module('app.directives')
.directive "mmAwacModalConsultEvent", (directiveService, downloadService, translationService, messageFlash,ngTableParams,$filter) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-consult-event.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope


        downloadService.getJson '/awac/organization/events/byOrganization/'+$scope.getParams().organizationCustomer.name, (result) ->
            if not result.success
                $scope.isLoading = false
            else

                $scope.events = result.data.organizationEventList

                if $scope.events? and $scope.events.length > 0

                    if $scope.tableParams?
                        $scope.tableParams.reload();
                    else
                    $scope.tableParams = new ngTableParams(
                        page: 1 # show first page
                        count: 100 # count per page
                    ,
                        counts: []
                        total: 1
                        getData: ($defer, params) ->
                            orderedData = $filter("orderBy")($scope.events , params.orderBy())
                            $defer.resolve orderedData.slice((params.page() - 1) * params.count(),params.page() * params.count())
                            params.total(orderedData.length)
                    )
                $scope.isLoading = false

        $scope.close = ->
            modalService.close modalService.CONSULT_EVENT


