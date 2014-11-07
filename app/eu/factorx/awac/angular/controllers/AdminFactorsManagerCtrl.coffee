angular
.module('app.controllers')
.controller "AdminFactorsManagerCtrl", ($scope, $filter, $q, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.factors = null

    modalService.show(modalService.LOADING)

    $scope.loadPromise = downloadService.getJson "/awac/admin/factors/all", (result) ->
        modalService.close(modalService.LOADING)
        if result.success == true
            $scope.periods = result.data.periods
            $scope.factors = result.data.factors

            tt = $filter('translateText')

            for f in $scope.factors
                f.typeString = tt(f.indicatorCategory) + " / " + tt(f.activityType) + " / " + tt(f.activitySource)

            $scope.parameters = new ngTableParams({
                page: 1
                count: 10
                sorting:
                    indicatorCategory: 'asc'
            }, {
                total: $scope.factors.length,
                getData: ($defer, params) ->
                    orderedData = $scope.factors
                    orderedData = $filter('filter')(orderedData, params.filter())

                    params.total(orderedData.length)

                    if params.sorting()
                        orderedData = $filter('orderBy')(orderedData, params.orderBy())

                    $defer.resolve orderedData.slice((params.page() - 1) * params.count(),
                            params.page() * params.count())
            })


    $scope.save = ->
        modalService.show modalService.LOADING
        downloadService.postJson '/awac/admin/factors/update', {list: $scope.factors}, (result) ->
            modalService.close modalService.LOADING

    $scope.remove = (factor) ->

    $scope.names = (column) ->
        def = $q.defer()

        $q.all([ $scope.loadPromise ]).then () ->
            d = _.pluck($scope.factors, column)
            d = _.uniq(d)
            d = _.map d, (e) ->
                id: e,
                title: e

            def.resolve(d)

        return def
