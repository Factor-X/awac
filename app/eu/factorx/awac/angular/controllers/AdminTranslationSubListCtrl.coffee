angular
.module('app.controllers')
.controller "AdminTranslationSubListCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->

    $scope.subLists = []
    $scope.codeLabels = {}

    $scope.loadSubLists = () ->
        downloadService.getJson "awac/admin/translations/sublists/load", (result) ->
            if result.success
                $scope.subLists = result.data.sublists
                for i, codeLabelList of result.data.codeLabels
                    $scope.codeLabels[codeLabelList.code] = codeLabelHelper.sortCodeLabelsByNumericKey codeLabelList.codeLabels
            return
        return

    $scope.editSubList = (subList) ->
        params =
            subList: subList
            codeLabels: $scope.codeLabels[subList.referencedCodeList]
        modalService.show(modalService.EDIT_SUB_LIST, params)
        return

    $scope.getLabelByKey = (codeList, key) ->
        return codeLabelHelper.getLabelByKey($scope.codeLabels[codeList], key)

    $scope.loadSubLists()