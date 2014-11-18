angular
.module('app.controllers')
.controller "AdminFactorsManagerCtrl", ($scope, $filter, $q, $timeout, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.factors = null
    modalService.show(modalService.LOADING)

    #
    # Load
    #
    $scope.loadPromise = downloadService.getJson "/awac/admin/factors/all", (result) ->
        modalService.close(modalService.LOADING)
        if result.success == true
            $scope.periods = result.data.periods
            $scope.factors = result.data.factors
            $scope.originalFactors = angular.copy(result.data.factors)

            tt = $filter('translateText')

            for f in $scope.factors
                f.typeString = tt(f.indicatorCategory) + " / " + tt(f.activityType) + " / " + tt(f.activitySource)

            $scope.parameters = new ngTableParams({
                page: 1
                count: 5
                sorting:
                    indicatorCategory: 'asc'
            }, {
                counts: []
                total: $scope.factors.length
                getData: ($defer, params) ->
                    console.log 'getData'
                    orderedData = $scope.factors
                    orderedData = $filter('filter')(orderedData, params.filter())

                    params.total(orderedData.length)

                    if params.sorting()
                        orderedData = $filter('orderBy')(orderedData, params.orderBy())

                    $defer.resolve orderedData.slice((params.page() - 1) * params.count(),
                            params.page() * params.count())
                $scope: { $data: {} }
            })


    #
    # Check if factor is modified
    #
    $scope.isModified = (factor) ->
        original = _.find $scope.originalFactors, (o) ->
            return o.key == factor.key

        compared = _.omit(factor, 'typeString')

        eq = angular.equals(original, compared)

        return !eq

    #
    # Period options for factor and factorValue
    #
    $scope.getAvailablePeriods = (factor, fv) ->
        return _.reject $scope.periods, (e) ->
            return _.find factor.factorValues, (ee) ->
                if ee == fv
                    return false
                ee.dateIn == e.key

    #
    # "Add factor value" handler
    #
    $scope.addValue = (factor) ->
        availablePeriods = _.sortBy($scope.getAvailablePeriods(factor, null), 'label')
        lastUsed = _.max(factor.factorValues, (fv) ->
            fv.dateIn
        )

        index = _.sortedIndex(_.pluck(availablePeriods, 'key'), lastUsed.dateIn);

        dateIn = availablePeriods[index]?.key

        factor.factorValues.push
            __type: 'eu.factorx.awac.dto.admin.get.FactorValueDTO'
            id: null
            value: 0
            dateIn: dateIn
            dateOut: null

    $scope.removeFactorValue = (factor, fv) ->
        factor.factorValues = _.without(factor.factorValues, fv)

    #
    # "Save" handler
    #
    $scope.save = ->
        console.log $scope.factors
        modalService.show modalService.LOADING

        # adjust all dateOuts for modified
        for f in $scope.factors
            if $scope.isModified(f)
                



        downloadService.postJson '/awac/admin/factors/update', {__type: 'eu.factorx.awac.dto.awac.post.UpdateFactorsDTO', factors: $scope.factors}, (result) ->
            if result.success == true
                downloadService.getJson "/awac/admin/factors/all", (result) ->
                    modalService.close modalService.LOADING
                    if result.success == true
                        $scope.periods = result.data.periods
                        $scope.factors = result.data.factors
                        $scope.originalFactors = angular.copy(result.data.factors)

                        tt = $filter('translateText')

                        for f in $scope.factors
                            f.typeString = tt(f.indicatorCategory) + " / " + tt(f.activityType) + " / " + tt(f.activitySource)
                        $timeout () ->
                            $scope.parameters.reload()
                        , 500

    #
    # "Remove factor" handler
    #
    $scope.remove = (factor) ->
        noop = 0

    #
    # Names for search field in table
    #
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
