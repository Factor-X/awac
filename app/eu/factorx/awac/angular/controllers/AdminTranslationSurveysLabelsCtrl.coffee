angular
.module('app.controllers')
.controller "AdminTranslationSurveysLabelsCtrl", ($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->
    $scope.formLabelsByCalculator = {}
    $scope.initialLabels = {}
    $scope.waitingData = true
    $scope.isLoading = false

    $scope.loadCodeLabels = () ->
        downloadService.getJson "/awac/admin/translations/surveyslabels/load", (result) ->
            if result.success
                allFormLabels = result.data.forms
                $scope.initialLabels = angular.copy($scope.flattenCodeLabels(allFormLabels))
                console.log("$scope.initialLabels", $scope.initialLabels)
                allFormLabels = _.sortBy(allFormLabels, 'codeKey')
                $scope.formLabelsByCalculator = _.groupBy(allFormLabels, 'calculatorCodeKey')
            $scope.waitingData = false
            return
        return

    $scope.flattenCodeLabels = (formLabelsDTOs) ->
        res = []
        for form in formLabelsDTOs
            for questionSet in form.questionSets
                res.concat($scope.addQuestionSetLabels(res, questionSet))
        return _.indexBy(res, "key")

    $scope.addQuestionSetLabels = (res, questionSet) ->
        res.push(questionSet.label)
        for question in questionSet.questions
            res.push(question.label)
        for childQuestionSet in questionSet.children
            res.concat($scope.addQuestionSetLabels(res, childQuestionSet))
        return res

    $scope.save = () ->
        $scope.isLoading = true

        formLabelsDTOs = _.flatten(_.values($scope.formLabelsByCalculator))
        finalLabels = $scope.flattenCodeLabels(formLabelsDTOs)

        updatedCodeLabels = []
        for key, codeLabel of finalLabels
            initialLabel = $scope.initialLabels[key]
            if !angular.equals(codeLabel, initialLabel)
                if !(codeLabel.labelEn)
                    messageFlash.displayError "Le champ libellé EN est obligatoire. Celui-ci n'est pas défini pour l'élément '" + codeLabel.key + "'"
                    $scope.isLoading = false
                    return
                updatedCodeLabels.push(codeLabel)
        console.log 'updatedCodeLabels', updatedCodeLabels
        if (updatedCodeLabels.length == 0)
            messageFlash.displayError "Vous n'avez effectué aucun changement"
            $scope.isLoading = false
            return

        data =
            codeLabelsByList: {'QUESTION': updatedCodeLabels}
        downloadService.postJson "/awac/admin/translations/surveyslabels/save", data, (result) ->
            if result.success
                $scope.initialLabels = angular.copy(finalLabels)
                messageFlash.displaySuccess translationService.get "CHANGES_SAVED"
            $scope.isLoading = false


    #    $scope.asJsObj = (questionSet) ->
    #        return {
    #            key: questionSet.label.key
    #        }

    $scope.getJsonObj = (strQuestionSet) ->
        return strQuestionSet;

    $scope.loadCodeLabels()