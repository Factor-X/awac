angular
.module('app.controllers')
.controller "AdminTranslationBaseListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu
    fixedLists = ['ActivityCategory', 'ActivitySource', 'ActivitySubCategory', 'ActivityType', 'IndicatorCategory',
                  'BASE_INDICATOR', 'INDICATOR', 'INTERFACE_TYPE', 'REDUCING_ACTION_STATUS', 'REDUCING_ACTION_TYPE']

    $scope.groupsLabels = ["Listes 'système'", "Listes liées aux actions", "Listes liées aux questionnaires"]

    #    $scope.codeLabels = {}
    $scope.waitingData = true
    $scope.isLoading = false

    $scope.codeLabelToAdd = {
        key: ""
        labelEn: ""
        labelFr: ""
        labelNl: ""
        clear: () ->
            this.key = ""
            this.labelEn = ""
            this.labelFr = ""
            this.labelNl = ""
            return
    }

    $scope.sortableOptions =
        axis: 'y'

    $scope.allBaseLists = []

    $scope.loadBaseLists = () ->
        downloadService.getJson "awac/admin/translations/baselists/load", (result) ->
            if result.success
                allBaseLists = result.data.baseLists
                $scope.allBaseLists = [getSystemBaseLists(allBaseLists), getActionsBaseLists(allBaseLists), getSurveysBaseLists(allBaseLists)]
            $scope.waitingData = false
            return
        return

    getSystemBaseLists = (allBaseLists) ->
        res = []
        res[0] = _.findWhere(allBaseLists, {codeList: 'ActivitySource'})
        res[1] = _.findWhere(allBaseLists, {codeList: 'ActivityType'})
        res[2] = _.findWhere(allBaseLists, {codeList: 'ActivityCategory'})
        res[3] = _.findWhere(allBaseLists, {codeList: 'ActivitySubCategory'})
        res[4] = _.findWhere(allBaseLists, {codeList: 'IndicatorCategory'})
        res[5] = _.findWhere(allBaseLists, {codeList: 'INDICATOR'})
        res[6] = _.findWhere(allBaseLists, {codeList: 'BASE_INDICATOR'})
        res[7] = _.findWhere(allBaseLists, {codeList: 'INTERFACE_TYPE'})
        return res

    getActionsBaseLists = (allBaseLists) ->
        res = []
        res[0] = _.findWhere(allBaseLists, {codeList: 'REDUCING_ACTION_TYPE'})
        res[1] = _.findWhere(allBaseLists, {codeList: 'REDUCING_ACTION_STATUS'})
        return res

    getSurveysBaseLists = (allBaseLists) ->
        res = _.filter allBaseLists, (baseList) ->
            return ! _.contains(fixedLists, baseList.codeList)
        return _.sortBy res, (baseList) ->
            return baseList.codeList

    $scope.getCalculatorByKeyPrefix = (codeKey) ->
        if codeKey.startsWith("I_") || codeKey.startsWith("BI_")
            return "enterprise"
        if codeKey.startsWith("ICo_") || codeKey.startsWith("BICo_")
            return "municipality"
        return


    $scope.sortItems = (baseLists) ->
        res = _.sortBy(baseLists, "codeList")
        for baseList in res
            baseList.codeLabels = _.sortBy(baseList.codeLabels, "orderIndex")
        return res

    $scope.addCodeLabel = (baseList) ->
        if !(!!$scope.codeLabelToAdd.key && !!$scope.codeLabelToAdd.labelEn)
            messageFlash.displayError "Echec de l'ajout: les champs 'clé' et 'Libellé EN' sont obligatoires!"
            return
        if _.contains(_.pluck(baseList.codeLabels, 'key'), $scope.codeLabelToAdd.key)
            messageFlash.displayError "Echec de l'ajout: la clé '" + $scope.codeLabelToAdd.key + "' est déjà présente dans cette liste!"
            return
        baseList.codeLabels.push(angular.copy($scope.codeLabelToAdd))
        console.log("$scope.baseLists", $scope.baseLists)
        console.log("baseList.codeLabels", baseList.codeLabels)
        $scope.codeLabelToAdd.clear()
        return

    $scope.save = () ->
        $scope.isLoading = true

        # update orderIndex field
        for baseList in $scope.baseLists
            for index, codeLabel of baseList.codeLabels
                codeLabel.orderIndex = +index + 1

        data = {baseLists: $scope.baseLists}

        downloadService.postJson "/awac/admin/translations/baselists/save", data, (result) ->
            $scope.isLoading = false
            if result.success
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
            console.log("result.data", result.data)
            angular.extend($scope.baseLists, $scope.sortItems(result.data.baseLists))

            return


    $scope.loadBaseLists()