angular
.module('app.controllers')
.controller "AdminTranslationBaseListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->

    $scope.baseLists = []
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

    $scope.loadBaseLists = () ->
        downloadService.getJson "awac/admin/translations/baselists/load", (result) ->
            if result.success
                $scope.baseLists = $scope.sortItems(result.data.baseLists)
            $scope.waitingData = false
            return
        return

    $scope.sortItems = (baseLists) ->
        res = _.sortBy(baseLists, "codeList")
        for baseList in res
            baseList.codeLabels = _.sortBy(baseList.codeLabels, "orderIndex")
        return res

    $scope.addCodeLabel = (baseList) ->
        if ! (!!$scope.codeLabelToAdd.key && !!$scope.codeLabelToAdd.labelEn)
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