angular
.module('app.controllers')
.controller "AdminFactorsManagerCtrl", ($scope, $filter, $q, $timeout, $location, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    $scope.factors = null
    $scope.newFactor = {
        __type: 'eu.factorx.awac.dto.awac.post.CreateFactorDTO'
        valueSince2000: 0.0
    }

    modalService.show(modalService.LOADING)

    #
    # Load
    #
    $scope.loadPromise = downloadService.getJson "/awac/admin/factors/all", (result) ->
        modalService.close(modalService.LOADING)
        if result.success == true
            $scope.periods = result.data.periods
            $scope.factors = result.data.factors
            $scope.newFactor.indicatorCategories = result.data.indicatorCategories
            $scope.newFactor.activityTypes = result.data.activityTypes
            $scope.newFactor.activitySources = result.data.activitySources
            $scope.newFactor.unitCategories = result.data.unitCategories

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
        params =
            titleKey: "FACTORS_DELETE_CONFIRMATION_TITLE"
            messageKey: "FACTORS_DELETE_CONFIRMATION_MESSAGE"
            onConfirm: () ->
                factor.factorValues = _.without(factor.factorValues, fv)

        modalService.show modalService.CONFIRM_DIALOG, params


    $scope.reload = () ->
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

    $scope.exportXls = () ->
        modalService.show modalService.LOADING
        downloadService.getJson "/awac/admin/factors/export", (result) ->
            modalService.close modalService.LOADING

    #
    # "Save" handler
    #
    $scope.save = ->
        if $scope.hasEdits()
            modalService.show modalService.LOADING

            # adjust all dateOuts for modified
            for f in $scope.factors
                if $scope.isModified(f)

                    fvs = _.sortBy(f.factorValues, 'dateIn')

                    for i in [1...fvs.length]
                        fvs[i - 1].dateOut = '' + (fvs[i].dateIn - 1)

                    f.factorValues = fvs

            downloadService.postJson '/awac/admin/factors/update', {__type: 'eu.factorx.awac.dto.awac.post.UpdateFactorsDTO', factors: $scope.factors}, (result) ->
                if result.success == true
                    $scope.reload()


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

    $scope.createFactor = () ->
        modalService.show modalService.LOADING
        downloadService.postJson '/awac/admin/factors/create', $scope.newFactor, (result) ->
            modalService.close modalService.LOADING
            if result.success == true
                $scope.reload()

    $scope.hasEdits = () ->
        return _.any($scope.factors, $scope.isModified)


    $scope.ignoreChanges = false
    $scope.$root.$on '$locationChangeStart', (event, next, current) ->
        return unless next != current
        return unless $scope.hasEdits() and !$scope.ignoreChanges

        # stop changing
        event.preventDefault()

        # show confirm
        params =
            titleKey: "FACTORS_CANCEL_CONFIRMATION_TITLE"
            messageKey: "FACTORS_CANCEL_CONFIRMATION_MESSAGE"
            onConfirm: () ->
                $scope.ignoreChanges = true
                $location.path(next.split('#')[1])
        modalService.show modalService.CONFIRM_DIALOG, params









